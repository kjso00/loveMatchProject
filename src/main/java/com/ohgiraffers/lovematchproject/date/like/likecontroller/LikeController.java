package com.ohgiraffers.lovematchproject.date.like.likecontroller;

import com.ohgiraffers.lovematchproject.date.dateexception.DateException;
import com.ohgiraffers.lovematchproject.date.dateexception.DateNotFoundException;
import com.ohgiraffers.lovematchproject.date.dateexception.DateQuotaExceededException;
import com.ohgiraffers.lovematchproject.date.dateexception.InvalidDateOperationException;
import com.ohgiraffers.lovematchproject.date.like.likeservice.LikeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/likes")
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping("/{dateId}")
    public ResponseEntity<String> toggleLike(@PathVariable Long dateId) {
        try {
            likeService.toggleLike(dateId);
            return ResponseEntity.ok("Like toggled successfully");
        } catch (DateNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (DateQuotaExceededException e) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(e.getMessage());
        } catch (InvalidDateOperationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (DateException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}