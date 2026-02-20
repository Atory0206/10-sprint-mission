package com.sprint.mission.discodeit.message.controller;

import com.sprint.mission.discodeit.message.dto.ReadStatusCreateRequest;
import com.sprint.mission.discodeit.message.dto.ReadStatusResponse;
import com.sprint.mission.discodeit.message.dto.ReadStatusUpdateRequest;
import com.sprint.mission.discodeit.message.entity.ReadStatus;
import com.sprint.mission.discodeit.message.service.ReadStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/readStatus")
@RequiredArgsConstructor
public class ReadStatusController {

    private final ReadStatusService readStatusService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReadStatus> createReadStatus (@RequestBody ReadStatusCreateRequest request){
        ReadStatus readStatus = readStatusService.create(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(readStatus);
    }

    @RequestMapping(method = RequestMethod.PATCH)
    public ResponseEntity<ReadStatus> updateReadStatus (@RequestParam UUID readStatusId,
            @RequestBody ReadStatusUpdateRequest request) {
        ReadStatus readStatus = readStatusService.update(readStatusId,request);
        return  ResponseEntity.
                status(HttpStatus.OK)
                .body(readStatus);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<ReadStatus>> findAllByUserId(@RequestParam(name = "userId") UUID id) {
        List<ReadStatus> readStatuses = readStatusService.findAllByUserId(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(readStatuses);
    }
}
