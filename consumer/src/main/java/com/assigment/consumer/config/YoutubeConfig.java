package com.assigment.consumer.config;

import com.assigment.consumer.model.YouTubeProperties;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.youtube.YouTube;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Configuration
public class YoutubeConfig {

    @Autowired
    private YouTubeProperties youTubeProperties;

    @Bean
    public YouTube youTube() throws GeneralSecurityException, IOException {
        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = new GsonFactory();
        return new YouTube.Builder(httpTransport, jsonFactory, null).build();
    }

    @Bean
    public YouTube.Videos.List createVideosRequest(YouTube youTube) throws IOException {
        YouTube.Videos.List videoList = youTube.videos().list(List.of("snippet", "contentDetails", "statistics"));
        videoList.setKey(youTubeProperties.getKey());
        return videoList;
    }

    @Bean
    public YouTube.CommentThreads.List createCommentRequest(YouTube youTube) throws IOException {
        YouTube.CommentThreads.List commentThreadsList = youTube.commentThreads().list(List.of("snippet"));
        commentThreadsList.setKey(youTubeProperties.getKey());
        return commentThreadsList;
    }

}
