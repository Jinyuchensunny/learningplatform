package com.learningplatform.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonArray;
import com.learningplatform.mapper.*;
import com.learningplatform.pojo.*;
import com.learningplatform.util.Answer;
import netscape.javascript.JSObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RoomController {
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    EduVideoSourceMapper eduVideoSourceMapper;
    @Autowired
    ExerciseMapper exerciseMapper;
    @Autowired
    EduStudioMapper eduStudioMapper;
    @Autowired
    ColleageMapper colleageMapper;
    @Autowired
    SysUserMapper sysUserMapper;

    @RequestMapping("/toStudy")
    public String toStudy(String studioId, String userId, Model model) {
        Integer sid = Integer.parseInt(studioId);
        Integer uid = Integer.parseInt(userId);
        List<Task> tasks = taskMapper.findTaskByUserId(uid);
        List<Edu_videosource> videos = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getStatus() == 1) {
                Edu_videosource video = eduVideoSourceMapper.findVideoById(task.getVideoId());
                if (video.getStudioId() == sid) {
                    videos.add(video);
                }
            }
        }
        model.addAttribute("videos", videos);
        model.addAttribute("userId", uid);
        return "study";
    }

    @RequestMapping("/watchVideo")
    public String watchVideo(String userId, String videoId, Model model) {
        Integer uid = Integer.parseInt(userId);
        Integer vid = Integer.parseInt(videoId);
        Edu_videosource video = eduVideoSourceMapper.findVideoById(vid);
        List<Exercise> exercises = exerciseMapper.findExerciseByVideoId(vid);
        model.addAttribute("userId", uid);
        model.addAttribute("video", video);
        model.addAttribute("exercises", exercises);
        return "watchVideo";
    }
    @RequestMapping("/bonus")
    public String bonus() {

        return "bonus";
    }

    @RequestMapping("/watchFinished")
    public String watchFinished(String userId, String videoId, Model model) {
        Integer uid = Integer.parseInt(userId);
        Integer vid = Integer.parseInt(videoId);
        List<Task> tasks = taskMapper.findTaskByUserId(uid);
        for (Task task : tasks) {
            //System.out.println(task.getVideoId());
            if (task.getVideoId() == vid) {
                task.setStatus(2);
                taskMapper.updateTaskStatus(task);
            }
        }
        Edu_videosource video = eduVideoSourceMapper.findVideoById(vid);
        model.addAttribute("studioId", video.getStudioId());
        model.addAttribute("userId", uid);
        return "room";
    }

    @RequestMapping("/getBonus")
    public String getBonus(String userId, String studioId, Model model) {
        Integer uid=Integer.parseInt(userId);
        Sys_user user=sysUserMapper.findUserByUserId(uid);
        model.addAttribute("nickName","昵称："+user.getUser_name());
        model.addAttribute("loginName","用户名："+user.getLogin_name());
        model.addAttribute("userId",userId);
        model.addAttribute("studioId",studioId);
        int sum=0;
        int sum1=0;
        List<Task> tasks = taskMapper.findTaskByUserId(uid);
        for (Task task : tasks) {
            if (task.getType()==1&&task.getStatus()==3 ||task.getType()==2&&task.getStatus()==2) {
                sum++;
            }
        }
        sum1=sum;
        sum/=4;
        model.addAttribute("sum",sum);
        model.addAttribute("sum1",sum1);
        return "bonus";
    }

    @RequestMapping("/exercise")
    public String exercise(String userId, String studioId, Model model) {
        Integer uid=Integer.parseInt(userId);
        Integer sid=Integer.parseInt(studioId);
        List<Task> tasks=taskMapper.findTaskByUserId(uid);
        //List<Exercise> exercises=new ArrayList<>();
        JSONArray allExercises =new JSONArray();
        for(Task task :tasks){
            if(task.getStatus()==2&&task.getType()==1){
                List<Exercise> eList=exerciseMapper.findExerciseByVideoId(task.getVideoId());
                Edu_videosource video=eduVideoSourceMapper.findVideoById(task.getVideoId());
                JSONObject exercises =new JSONObject();
                exercises.put("videoId",video.getVideoId());
                exercises.put("videoName",video.getName());
                String str="";
                int idx=0;
                for(Exercise e:eList){
                    idx++;
                    str=str+"*题"+idx+":（"+e.getContent()+"）";
                }
                exercises.put("content",str);
                allExercises.add(exercises);
            }
        }
        model.addAttribute("exercises",allExercises);
        Answer answer=new Answer();
        model.addAttribute("userId",uid);
        model.addAttribute("answer",answer);
        model.addAttribute("studioId",studioId);
        return "exercise";
    }

    @RequestMapping("/exerciseSuccess")
    public String exerciseSuccess(Answer answer,String userId,String studioId, Model model) {
        // System.out.println(answer.toString());
        // System.out.println(userId);

        Integer uid=Integer.parseInt(userId);
        Integer vid=answer.getVideoId();
        String ans=answer.getContent();
        String msg="";

        List<Task> tasks=taskMapper.findTaskByUserId(uid);
        Task task=new Task();
        if(ans.equals("")||vid==null) msg="答案或视频号为空,提交失败！";
        else{
            int flag=0;
            for(Task t:tasks){
                if(t.getStatus()==2&&t.getType()==1&&t.getVideoId()==vid) {task=t;flag=1;}
            }

            if(flag==0) msg="视频号有误，提交失败！";
            else{
                task.setAnswer(ans);
                task.setStatus(3);
                Timestamp now = new Timestamp(System.currentTimeMillis());
                task.setFinishTime(now);
                taskMapper.updateTaskAnswer(task);
            }
        }
        if(msg.equals("")) msg="提交成功，您已完成该任务！";
        model.addAttribute("msg",msg);
        model.addAttribute("userId",userId);
        model.addAttribute("studioId",studioId);
        return "exerciseSuccess";
    }

    //http://localhost:8080/room?userId=259&studioId=5
    @RequestMapping("/selfStatus")

    public String selfStatus(String studioId, String userId, Model model) {
        Integer uid = Integer.parseInt(userId);
        Integer sid = Integer.parseInt(studioId);
        List<Task> tasks = taskMapper.findTaskByUserId(uid);
        Integer sum = 0;
        for (Task task : tasks) {
            if (task.getStatus() == 2) {
                sum++;
            }
        }
        Edu_studio edu_studio = eduStudioMapper.findStudioByStudioId(sid);
        String college = colleageMapper.findColleageNameByColleageId(edu_studio.getColleageId());
        String name = sysUserMapper.findLoginNameByUserId(uid);
        model.addAttribute("sum", sum);
        model.addAttribute("studio", edu_studio.getName());
        model.addAttribute("college", college);
        model.addAttribute("name", name);
        return "information";
    }
}
