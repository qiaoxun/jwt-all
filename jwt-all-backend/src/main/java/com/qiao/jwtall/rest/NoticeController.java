package com.qiao.jwtall.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * No authentication needed
 */
@RestController
@RequestMapping("notice")
public class NoticeController {

    @RequestMapping("getNewestNotice")
    public String getNewestNotice() {
        return "Ukraine 'belongs' in EU - Von der Leyen";
    }

}
