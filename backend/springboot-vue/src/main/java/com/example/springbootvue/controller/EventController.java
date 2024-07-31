package com.example.springbootvue.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.springbootvue.common.Result;
import com.example.springbootvue.entity.Event;
import com.example.springbootvue.entity.EventSchema;
import com.example.springbootvue.entity.EventTheme;
import com.example.springbootvue.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.*;

@CrossOrigin
@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    ESEventService esEventService;

    @Autowired
    EventTextEmbeddingService eventTextEmbeddingService;

    @Autowired
    EventSchemaService eventSchemaService;

    @Autowired
    EventThemeService eventThemeService;

    String images = "";

    @PostMapping("/add")
    public Result add(@RequestBody Event event) throws Exception{
        //esEventService.insertEvent(event);
        //eventTextEmbeddingService.insertSingleEventWithTextEmbedding(event, "event");
        if(!event.getImage().isEmpty() && !images.isEmpty()) {
            //System.out.println(event.getImage());
            images = images.substring(0, images.length() - 1);
            event.setImage(images);
            images = "";
        }
        //System.out.println(event);
        eventTextEmbeddingService.insertSingleEventWithTextEmbedding(event, "event");
        return Result.success(event);
    }

    @PostMapping("/uploadPicture")
    public Result uploadPicture(MultipartFile file) throws Exception {
        BufferedImage image = ImageIO.read(file.getInputStream());
        //System.out.println(file.getOriginalFilename());
        String fileName = UUID.randomUUID().toString();
        //String str_time = Long.toString(System.currentTimeMillis());
        //File imageFile = new File("C:\\Users\\86182\\Desktop\\test\\" + fileName + "-" + str_time + ".jpg");
        //File imageFile = new File("C:\\Users\\86182\\Desktop\\test\\" + fileName + ".jpg");
        File imageFile = new File("D:\\vue\\myvue3\\src\\assets\\pictures\\" + fileName + ".jpg");
        ImageIO.write(image, "jpg", imageFile);
        images = images + fileName + ".jpg,";
        return Result.success();
    }
//    @PostMapping("/try")
//    public Result mytry(MultipartFile[] files) throws IOException, Exception {
//        System.out.println(files);
//        return Result.success();
//    }

    @PostMapping("/importWithJson")
    public Result importEventsWithJson(MultipartFile file) throws IOException, Exception {
        StringBuilder sb = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        JSONArray arr = JSON.parseArray(sb.toString());
        List<Event> events = new ArrayList<Event>();
        //System.out.println(arr.size());
        for(int i = 0;i < arr.size();i++) {
            Event event = new Event();

            JSONObject json = JSONObject.parseObject(arr.get(i).toString());
            //String content = json.getString("content");
            //String des = content.split("\n", 2)[1];
            String title = json.getString("title");
            String time = json.getString("time");
            String image = json.getString("image");
            String des = json.getString("content");
            event.setTitle(title);
            event.setTime(time);
            event.setImage(image);
            event.setDescription(des);


            String summary = json.getString("summary");
            JSONObject summary_json = JSONObject.parseObject(summary);
            String event_arguments = summary_json.getString("事件论元");
            String[] arguments = event_arguments.split(",");
            List<String> list = new ArrayList<>(arguments.length);
            Collections.addAll(list, arguments);

            //事件类型是否存在
            String event_type = summary_json.getString("事件类型");
            if(event_type != null) {
                List<EventSchema> schema = eventSchemaService.selectByTypeName(event_type);
                if(schema.size() == 0){
                    return Result.error("无事件类型！请先导入\'" + event_type + "\'类型。");
                }
                String type_id = schema.get(0).getId();
                event.setTypeId(type_id);
                event.setTypeName(event_type);
            }
            //事件专题是否存在
            String event_theme = summary_json.getString("事件专题");
            if(event_theme != null) {
                List<EventTheme> theme = eventThemeService.selectByThemeName(event_theme);
                if(theme.size() == 0) {
                    return Result.error("无事件专题！请先导入\'" + event_theme + "\'专题。");
                }
                String theme_id = theme.get(0).getId();
                event.setThemeId(theme_id);
                event.setThemeName(event_theme);
            }
            event.setArguments(list);
            events.add(event);
            //esEventService.insertEvent(event);
            //System.out.println(event);
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    eventTextEmbeddingService.insertWithBulk(events, "event");
                } catch(Exception e) {
                    //e.printStackTrace();
                }
            }
        }).start();
        //esEventService.insertEvents(events);
        //eventTextEmbeddingService.insertEventsWithTextEmbedding(events, "event");
        //eventTextEmbeddingService.insertWithBulk(events);
        //System.out.println(file.getOriginalFilename());

        return Result.success("插入成功！");
    }

    @PostMapping("/deleteById")
    public Result deleteById(@RequestParam String id){
        esEventService.deleteEvent(id);
        return Result.success("删除成功！");
    }

    @GetMapping("/allPageable")
    public Result selectAllPageable(@RequestParam Integer pageNumber, @RequestParam Integer pageSize){
        Page<Event> events = esEventService.findAllPageable(PageRequest.of(pageNumber - 1, pageSize));
        return Result.success(events);
    }

    @GetMapping("imageNotEmpty")
    public Result selectImageNotEmptyPageable(@RequestParam Integer pageNumber, @RequestParam Integer pageSize) {
        Page<Event> events = esEventService.findImageNotEmpty(PageRequest.of(pageNumber - 1, pageSize));
        return Result.success(events);
    }

    @GetMapping("/allById")
    public Result selectById(@RequestParam String id) {
        Optional<Event> event = esEventService.findEventById(id);
        return Result.success(event);
    }

    @GetMapping("/allByTitle")
    public Result selectByTitle(@RequestParam String title) {
        List<Event> events = esEventService.findEventByTitle(title);
        return Result.success(events);
    }

    @GetMapping("/allByDescription")
    public Result selectByDescription(@RequestParam String des) {
        List<Event> events = esEventService.findEventByDescription(des);
        return Result.success(events);
    }

    @GetMapping("/allByTheme")
    public Result selectByTheme(@RequestParam String theme) {
        List<Event> events = esEventService.findEventByThemeId(theme);
        return Result.success(events);
    }

    @GetMapping("/findByDescriptionPageable")
    public Result selectByPage(@RequestParam Integer pageNumber,
                               @RequestParam Integer pageSize,
                               @RequestParam String des,
                               @RequestParam(required = false) String eventType,
                               @RequestParam(required = false) String eventTheme){
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize);
        //System.out.println(eventType);
        //System.out.println(eventTheme);
        if(eventTheme != null && eventType != null && !eventTheme.equals("") && !eventType.equals("") ) {
            Page<Event> page = esEventService.findEventByDescriptionAndEventTypeAndEventThemePageable(des, eventType, eventTheme, pageable);
            return Result.success(page);
        }
        else {
            if(eventTheme != null && !eventTheme.equals("")) {
                Page<Event> page = esEventService.findEventByDescriptionAndEventThemePageable(des, eventTheme, pageable);
                return Result.success(page);
            }
            if(eventType != null && !eventType.equals("")) {
                //System.out.println(eventType);
                Page<Event> page = esEventService.findEventByDescriptionAndEventTypePageable(des, eventType, pageable);
                return Result.success(page);
            }
        }
        Page<Event> page = esEventService.findEventByDescriptionPageable(des, pageable);
        return Result.success(page);
    }

    @GetMapping("/findBySemanticKNN")
    public Result selectBySemanticPageable(@RequestParam Integer pageNumber,
                                           @RequestParam Integer pageSize,
                                           @RequestParam String des,
                                           @RequestParam Integer k) throws Exception{
        List<Event> allEvents = eventTextEmbeddingService.searchByKNN("event", des, k);
        int fromIndex = (pageNumber - 1) * pageSize;
        int toIndex = pageNumber * pageSize;
        List<Event> indexEvents = allEvents.subList(Math.min(fromIndex, allEvents.size()), Math.min(toIndex, allEvents.size()));
        Page<Event> page = new PageImpl(indexEvents, PageRequest.of(pageNumber - 1, pageSize), allEvents.size());

        return Result.success(page);
    }

    @GetMapping("/detail")
    public Result getDetails(@RequestParam String id) {
        Optional<Event> event = esEventService.findEventById(id);
        return Result.success(event);
    }


}
