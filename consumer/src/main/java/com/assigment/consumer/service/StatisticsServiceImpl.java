package com.assigment.consumer.service;

import com.assigment.consumer.model.Statistics;
import com.assigment.core.service.statistics.proto.Stataistics;
import com.assigment.core.service.statistics.proto.StatisticsServiceGrpc;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.CommentThread;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoListResponse;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    private YouTube.Videos.List videoListRequest;

    @Autowired
    private YouTube.CommentThreads.List commentThreadRequest;

    @GrpcClient("core-service")
    private StatisticsServiceGrpc.StatisticsServiceBlockingStub grpcStatisticsService;

    @Override
    public void updateStatisticsForUser(Statistics statistics, String username) {
        Stataistics.UpdateStatisticsForUserRequest grpcRequest = Stataistics.UpdateStatisticsForUserRequest
                .newBuilder()
                .setCountryCode(statistics.getCountryCode())
                .setCommentLink(statistics.getCommentLink())
                .setVideoLink(statistics.getVideoLink())
                .setUsername(username)
                .build();

        Stataistics.UpdateStatisticsForUserResponse grpcResponse = grpcStatisticsService.updateStatisticsForUser(grpcRequest);
    }

    @Override
    public Statistics getStatisticsByCountryCode(String countyCode) throws IOException {
        String videoLink = "";
        String commentLink = "";

        VideoListResponse videoList = videoListRequest.setChart("mostPopular")
                .setRegionCode(countyCode)
                .setMaxResults(1L)
                .execute();

        if (videoList != null && !videoList.isEmpty()) {
            Video video = videoList.getItems().get(0);
            videoLink = "https://www.youtube.com/watch?v=" + video.getId();

            String videoId = video.getId();

            commentThreadRequest.setVideoId(videoId);
            commentThreadRequest.setTextFormat("plainText");
            commentThreadRequest.setMaxResults(1L);

            List<CommentThread> commentThreadList = commentThreadRequest.execute().getItems();

            if (commentThreadList != null && !commentThreadList.isEmpty()) {
                CommentThread commentThread = commentThreadList.get(0);
                commentLink = "https://www.youtube.com/watch?v=" + video.getId() + "&lc=" + commentThread.getSnippet().getTopLevelComment().getId();
            }
        }

        return Statistics.builder().countryCode(countyCode).commentLink(commentLink).videoLink(videoLink).build();
    }
}
