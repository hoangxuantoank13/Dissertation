/*
 * Copyright (c) 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package cpcrawler.app;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.Joiner;
import cpcrawler.auth.Auth;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.Channel;
import com.google.api.services.youtube.model.ChannelListResponse;
import com.google.api.services.youtube.model.ChannelStatistics;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import com.google.common.collect.Lists;
import cpcrawler.ulti.Constant;
import java.io.File;
import java.io.FileInputStream;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import org.apache.commons.lang.mutable.MutableInt;


/**
 * Print a list of videos matching a search term.
 *
 * @author Jeremy Walker
 */
public class Search {

    /**
     * Define a global variable that identifies the name of a file that contains
     * the developer's API key.
     */

    private static final long NUMBER_OF_VIDEOS_RETURNED = 50;

    /**
     * Define a global instance of a Youtube object, which will be used to make
     * YouTube Data API requests.
     */
    private static YouTube youtube;
    private static MutableInt _NUMBER_VIDEO = new MutableInt(0);

    /**
     * Initialize a YouTube object to search for videos on YouTube. Then display
     * the name and thumbnail image of each video in the result set.
     *
     * @param args command line args.
     */
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        // Read the developer key from the properties file.
        Properties properties = new Properties();
        try {
            File f = new File("./" + Constant.PROPERTIES_FILENAME);
            InputStream in = new FileInputStream(f);
            properties.load(in);

        } catch (IOException e) {
            System.err.println("There was an error reading " + Constant.PROPERTIES_FILENAME + ": " + e.getCause()
                    + " : " + e.getMessage());
            System.exit(1);
        }

        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube");
        try {

            Credential credential = Auth.authorize(scopes, "channelbulletin");
            // This object is used to make YouTube Data API requests. The last
            // argument is required, but since we don't need anything
            // initialized when the HttpRequest is initialized, we override
            // the interface and provide a no-op function.
//            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, new HttpRequestInitializer() {
//                public void initialize(HttpRequest request) throws IOException {
//                }
//            }).setApplicationName("youtube-cmdline-search-sample").build();

            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential).setApplicationName(
                    "youtube-cmdline-channelbulletin-sample").build();

            // Prompt the user to enter a query term.
            String queryTerm = getInputQuery();

            // Define the API request for retrieving search results.
            YouTube.Search.List search = youtube.search().list("id,snippet");

            // Set your developer key from the {{ Google Cloud Console }} for
            // non-authenticated requests. See:
            // {{ https://cloud.google.com/console }}
            String apiKey = properties.getProperty("youtube.apikey");
            search.setKey(apiKey);
            search.setQ(queryTerm);
            search.setOrder("viewCount");
            search.setPublishedAfter(new DateTime(System.currentTimeMillis() - 86400000 * 7));

            // Restrict the search results to only include videos. See:
            // https://developers.google.com/youtube/v3/docs/search/list#type
            search.setType("video");

            // To increase efficiency, only retrieve the fields that the
            // application uses.
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url), nextPageToken");
            search.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);

            // Call the API and print results.
            String nextPageToken = "";
            List<List<SearchResult>> playlistItemList = new ArrayList<List<SearchResult>>();
            int i = 0;
            do {
                search.setPageToken(nextPageToken);
                SearchListResponse searchResponse = search.execute();

                List<SearchResult> searchResultList = searchResponse.getItems();
                playlistItemList.add(searchResultList);
                nextPageToken = searchResponse.getNextPageToken();
                i++;
            } while (nextPageToken != null);

            for (List<SearchResult> searchResultList : playlistItemList) {
                List<String> videoIds = new ArrayList<String>();
                if (searchResultList != null) {
                    for (SearchResult searchResult : searchResultList) {
                        videoIds.add(searchResult.getId().getVideoId());
                    }
                    Joiner stringJoiner = Joiner.on(',');
                    String videoId = stringJoiner.join(videoIds);

                    // Call the YouTube Data API's youtube.videos.list method to
                    // retrieve the resources that represent the specified videos.
                    YouTube.Videos.List listVideosRequest = youtube.videos().list("snippet, recordingDetails, statistics").setId(videoId);
                    VideoListResponse listResponse = listVideosRequest.execute();

                    List<Video> videoList = listResponse.getItems();

                    if (videoList != null) {
                        prettyPrint(videoList.iterator(), queryTerm, _NUMBER_VIDEO);
                    }
                }
            }
            long end = System.currentTimeMillis();
            long dis = end - start;
            System.out.println("Time: " + dis / 1000);
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    /*
     * Prompt the user to enter a query term and return the user-specified term.
     */
    private static String getInputQuery() throws IOException {

        String inputQuery = "samsung galaxy s8";

//        System.out.print("Please enter a search term: ");
//        BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
//        inputQuery = bReader.readLine();
//
//        if (inputQuery.length() < 1) {
//            // Use the string "YouTube Developers Live" as a default.
//            inputQuery = "YouTube Developers Live";
//        }
        return inputQuery;
    }

    /*
     * Prints out all results in the Iterator. For each result, print the
     * title, video ID, and thumbnail.
     *
     * @param iteratorSearchResults Iterator of SearchResults to print
     *
     * @param query Search query (String)
     */
    private static void prettyPrint(Iterator<Video> iteratorVideoResults, String query, MutableInt i) throws IOException {

        System.out.println("\n=============================================================");
        System.out.println(
                "   First " + NUMBER_OF_VIDEOS_RETURNED + " videos for search on \"" + query + "\".");
        System.out.println("=============================================================\n");

        if (!iteratorVideoResults.hasNext()) {
            System.out.println(" There aren't any results for your query.");
        }
        while (iteratorVideoResults.hasNext()) {
            try {
                i.setValue(i.intValue() + 1);
                System.out.println("Video " + i.intValue());
                Video singleVideo = iteratorVideoResults.next();

                String channelId = singleVideo.getSnippet().getChannelId();
                YouTube.Channels.List channelRequest = youtube.channels().list("statistics");
                channelRequest.setId(channelId);
                channelRequest.setFields("items(statistics)");
                ChannelListResponse channelResult = channelRequest.execute();

                List<Channel> channelsList = channelResult.getItems();

                if (channelsList != null) {
                    for (Channel channel : channelsList) {
                        ChannelStatistics statistics = channel.getStatistics();
                        System.out.println(" Channel Subcribe Count: " + statistics.getSubscriberCount());
                        System.out.println(" Channel Video Count: " + statistics.getVideoCount());
                        System.out.println(" Channel View Count: " + statistics.getViewCount());

                    }
                }

//                Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();
//                GeoPoint location = singleVideo.getRecordingDetails().getLocation();
                System.out.println(" Video Id: " + singleVideo.getId());
//            System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
//            System.out.println(" Location: " + location.getLatitude() + ", " + location.getLongitude());
//            System.out.println(" Thumbnail: " + thumbnail.getUrl());
                System.out.println(" Video View Count: " + singleVideo.getStatistics().getViewCount());
                System.out.println(" Video Comment Count: " + singleVideo.getStatistics().getCommentCount());

                System.out.println("\n-------------------------------------------------------------\n");
            } catch (Exception e) {
                System.out.println("DKM co ex : " + e.getMessage());
            }

        }
    }

}
