package com.learningplatform.controller;

import com.alibaba.fastjson.JSONObject;
import com.learningplatform.mapper.*;
import com.learningplatform.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.util.*;

@Controller
public class MapController {
    @Autowired
    ColleageMapper colleageMapper;
    @Autowired
    EduStudioMapper eduStudioMapper;
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    EduVideoSourceMapper eduVideoSourceMapper;
    @Autowired
    EduBooksourceMapper eduBooksourceMapper;
    @Autowired
    SysUserMapper sysUserMapper;

    @RequestMapping("/map")//前往地图页面
    public String map(Model model) {
        List<Colleage> colleages = colleageMapper.findAllColleage();
        Colleage colleage = colleages.get(0);
        model.addAttribute("colleages", (colleages));
        return "china";
    }

    @RequestMapping("/studio")//跳转到工作室
    public String studio(String colleageId, String userId, Model model) {
        Integer cid = Integer.parseInt(colleageId);
        Integer uid = Integer.parseInt(userId);
        List<Edu_studio> studios = eduStudioMapper.findStudioByColleageId(cid);
        model.addAttribute("userId", uid);
        model.addAttribute("studios", studios);
        System.out.println(studios);
        return "studio";
    }

    @RequestMapping("/studiop")//跳转到工作室
    public String studiop() {

        return "games_fps";
    }

    @RequestMapping("/room")//跳转到房间
    public String room(String userId, String studioId, String taskId, String videoId, String bookId, Model model) {
        Integer uid = Integer.parseInt(userId);
        Integer sid = Integer.parseInt(studioId);
        Integer tid = Integer.parseInt(taskId);
        Integer vid = Integer.parseInt(videoId);
        Integer bid = Integer.parseInt(bookId);

        if (tid == -1) ;//任务领取
        else {
            Task t = taskMapper.findTaskById(tid);
            t.setStatus(1);
            Timestamp now = new Timestamp(System.currentTimeMillis());
            t.setReceiveTime(now);
            taskMapper.updateTask(t);
        }
        System.out.println("aaaa!!!!"+bid);
        if (vid != -1 || bid != -1) //完成视频书籍观看
        {
            List<Task> taskList = taskMapper.findTaskByUserId(uid);
            for (Task t : taskList) {
                if (((t.getVideoId() == vid) || (t.getBookId() == bid)) && t.getStatus() == 1) {
                    t.setStatus(2);
                    Timestamp now = new Timestamp(System.currentTimeMillis());
                    if(t.getType()==2) {t.setFinishTime(now);System.out.println("kkkkk");}
                    taskMapper.updateTask(t);
                }
            }
        }

        List<Task> tasksFromUid = taskMapper.findTaskByUserId(uid);

//        未领取
        List<Task> tasks0 = new ArrayList<Task>();
//        已领取
        List<Task> tasks1 = new ArrayList<Task>();
//        未完成
        List<Task> tasks2 = new ArrayList<Task>();

        for (Task t : tasksFromUid) {
            if (t.getStatus() == 0) {
                tasks0.add(t);
            } else if (t.getStatus() == 1 ) {
                tasks1.add(t);
            } else if ((t.getStatus() == 2 && t.getType() == 1) || t.getStatus() == 1 && t.getType() == 2) {
                tasks2.add(t);
            }
        }
        Task onetask = new Task();
        if (tasks0.size() != 0) {
            Integer minLevel = 15;
            for (Task task : tasks0) {
                if (task.getLevel() < minLevel) {
                    minLevel = task.getLevel();
                    onetask = task;
                }
            }
        }
        JSONObject onetaskJson = new JSONObject();
        if (onetask.getTaskId() == null) onetaskJson.put("iftask", "no");
        else {
            onetaskJson.put("iftask", "yes");
            onetaskJson.put("taskDetail", onetask.getDetail());
            onetaskJson.put("taskLevel", onetask.getLevel().toString());
            onetaskJson.put("taskId", onetask.getTaskId().toString());
            String str = "";
            if (onetask.getType() == 1) {
                Edu_videosource video = eduVideoSourceMapper.findVideoById(onetask.getVideoId());
                str = "请前往观看" + video.getName() + "(视频)";
            } else if (onetask.getType() == 2) {
                Edu_booksource book = eduBooksourceMapper.findBookById(onetask.getBookId());
                str = "请前往观看" + book.getName() + "(书籍)";
            }
            onetaskJson.put("taskname", str);
        }

        List<Edu_videosource> videos = new ArrayList<>();
        List<Edu_booksource> books = new ArrayList<>();
        for (Task t : tasks1) {
            if (t.getType() == 2) {
                Edu_booksource book = eduBooksourceMapper.findBookById(t.getBookId());
                if (book.getStudioId() == sid)
                    books.add(book);
            } else if (t.getType() == 1) {
                Edu_videosource video = eduVideoSourceMapper.findVideoById(t.getVideoId());
                if (video.getStudioId() == sid) {
                    videos.add(video);
                }
            }
        }

        List<Task> tasks = taskMapper.findTaskByUserId(uid);
        Integer sum = 0;
        String finishTask = "";
        Map finishStr = new HashMap<Integer, String>();
        finishStr.put(1, "，请再接再厉。");
        finishStr.put(2, "，请再接再厉。");
        finishStr.put(3, "，请再接再厉。");
        finishStr.put(4, "，请再接再厉。");
        finishStr.put(5, "，请再接再厉。");
        finishStr.put(6, "，太棒啦！");
        finishStr.put(7, "，太棒啦！");
        finishStr.put(8, "，太棒啦！");
        finishStr.put(9, "，非常厉害！！");
        finishStr.put(10, "，非常厉害！！");
        long minx = 1000000000;
        Task lastTask = new Task();
        Boolean flagLastTask = false;
        for (Task task : tasks) {
            if ((task.getStatus() == 3&&task.getType()==1)||(task.getStatus() == 2&&task.getType()==2)) {
                sum++;
                Timestamp now = new Timestamp(System.currentTimeMillis());
                long x = now.getTime() - task.getFinishTime().getTime();
                x = x / 1000;
                // System.out.println(x);
                //System.out.println(now);
                if (x < 60) {
                    finishTask = "恭喜您成功完成了" + task.getLevel().toString() + "级难度的任务" + finishStr.get(task.getLevel());
                }
                if (x < minx) {
                    lastTask = task;
                    flagLastTask = true;
                }
            }
        }
        String notFinishTaskInformation = "";

        if (tasks2.size() > 0) {
            Edu_videosource v = eduVideoSourceMapper.findVideoById(tasks2.get(0).getVideoId());
            notFinishTaskInformation = "您未完成的任务：(任务编号：" + tasks2.get(0).getTaskId() + "，视频" + v.getName() + "后的习题，进度 50%)...";
        } else if (tasks1.size() > 0) {
            int type = tasks1.get(0).getType();
            Integer veid = 0;
            Integer boid = 0;
            if (type == 1) {
                veid = tasks1.get(0).getVideoId();
            } else {
                boid = tasks1.get(0).getBookId();
            }
            if (type == 1) {
                Edu_videosource vedio = eduVideoSourceMapper.findVideoById(veid);
                notFinishTaskInformation = "您未完成的任务：(任务编号" + tasks1.get(0).getTaskId() + "，视频" + vedio.getName() + "以及习题，进度 0%)...";
            } else if (type == 2) {
                Edu_booksource book = eduBooksourceMapper.findBookById(boid);
                notFinishTaskInformation = "您未完成的任务：(任务编号" + tasks1.get(0).getTaskId() + "，阅读" + book.getName() + "，进度 0%)...";
            }
        }

        String lastTaskInformation = "";
        if (flagLastTask == true) {
            if (lastTask.getType() == 1) {
                Integer veid = lastTask.getVideoId();
                Edu_videosource vedio = eduVideoSourceMapper.findVideoById(veid);
                String strtime = (lastTask.getFinishTime().getYear() + 1900) + "-" + (lastTask.getFinishTime().getMonth() + 1) + "-" + lastTask.getFinishTime().getDate();
                lastTaskInformation = "您已完成的任务：(任务编号：" + lastTask.getTaskId() + "，视频" + vedio.getName() + "，难度：" + lastTask.getLevel() + "，时间：" + strtime + ")...";

            } else {
                Integer boid = lastTask.getBookId();
                Edu_booksource book = eduBooksourceMapper.findBookById(boid);
                String strtime = lastTask.getFinishTime().getYear() + "-" + lastTask.getFinishTime().getMonth() + "-" + lastTask.getFinishTime().getDay();
                lastTaskInformation = "您已完成的任务：(任务编号：" + lastTask.getTaskId() + "，阅读" + book.getName() + "，难度：" + lastTask.getLevel() + "，时间：" + strtime + ")...";
            }

        }

        Edu_studio edu_studio = eduStudioMapper.findStudioByStudioId(sid);
        String college = colleageMapper.findColleageNameByColleageId(edu_studio.getColleageId());
        String name = sysUserMapper.findLoginNameByUserId(uid);

        model.addAttribute("lastTaskInformation", lastTaskInformation);
        model.addAttribute("notFinishTask", notFinishTaskInformation);
        model.addAttribute("finishTask", finishTask);
        model.addAttribute("sum", sum);
        model.addAttribute("studio", edu_studio.getName());
        model.addAttribute("college", college);
        model.addAttribute("name", name);
        model.addAttribute("studioId", sid);
        model.addAttribute("userId", uid);
        model.addAttribute("onetask", onetaskJson);
        model.addAttribute("tasks", tasks1);
        model.addAttribute("videos", videos);
        model.addAttribute("books", books);
        return "room2";
    }
}
