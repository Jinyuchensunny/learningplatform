package com.learningplatform.controller;

import com.learningplatform.mapper.*;
import com.learningplatform.pojo.*;
import com.learningplatform.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    @Autowired
    ExerciseMapper exerciseMapper;
    @Autowired
    EduVideoSourceMapper eduVideoSourceMapper;
    @Autowired
    EduBooksourceMapper eduBooksourceMapper;
    @Autowired
    SysUserMapper sysUserMapper;
    @Autowired
    TaskMapper taskMapper;
    @Autowired
    ColleageMapper colleageMapper;
    @Autowired
    EduStudioMapper eduStudioMapper;
    @Autowired
    MessageMapper messageMapper;

    @RequestMapping("/adminIndex")
    public String adminIndex(Model model) {
        return "adminIndex";
    }

    @RequestMapping("/adminWelcome")
    public String adminWelcome(Model model) {
        return "adminWelcome";
    }

    @RequestMapping("/adminGuide")
    public String adminGuide(Model model) {
        return "adminGuide";
    }

    @RequestMapping("/adminMessage")
    public String adminMessage(Model model) {
        List<Message> messages = messageMapper.findAllMessage();
        model.addAttribute("messages", messages);
        return "adminMessage";
    }

    @RequestMapping("/adminPublishInfo")
    public String adminPublishInfo(Model model) {
        Information information = new Information();
        model.addAttribute("information", information);
        return "adminPublishInfo";
    }

    @RequestMapping("/adminPublishInfoResult")
    public String adminPublishInfoResult(Information information, Model model) {
        model.addAttribute("result", "发布成功！");
        return "adminPublishInfoResult";
    }

    @RequestMapping("/adminAllUser")
    public String adminAllUser(Model model) {
        List<Task> taskes = taskMapper.findAllTask();
        List<Task> tasks = new ArrayList<>();
        for (Task task : taskes) {
            Integer uid = task.getUserId();
            if (sysUserMapper.findUserByUserId(uid).getStatus() == 0)
                tasks.add(task);
        }
        model.addAttribute("tasks", tasks);
        return "adminAllUser";
    }

    @RequestMapping("/adminDelStudent")
    public String adminPermission(Model model) {
        DelStu student = new DelStu();
        model.addAttribute("student", student);
        return "adminDelStudent";
    }

    @RequestMapping("/adminUpdateStuInfo")
    public String adminUpdateStuInfo(Model model) {
        UpdateStu updateStu = new UpdateStu();
        model.addAttribute("updateStu", updateStu);
        return "adminUpdateStuInfo";
    }

    @RequestMapping("/adminUpdateUserPer")
    public String adminUpdateUserPer(Model model) {
        Permission permission = new Permission();
        model.addAttribute("permission", permission);
        return "adminUpdateUserPer";
    }

    @RequestMapping("/adminUpdateUserPerResult")
    public String adminUpdateUserPerResult(Permission permission, Model model) {
        String result = "";
        if (sysUserMapper.findUserByUserId(permission.getUser_id()) == null)
            result = "用户不存在，请重新输入";
        else {
            if (sysUserMapper.findUserByUserId(permission.getUser_id()).getStatus() == 1)
                result = "用户已经被删除，请重新输入";
            else if (sysUserMapper.findUserByUserId(permission.getUser_id()).getStatus() == 2)
                result = "该用户被冻结，请重新输入";
            else {
                sysUserMapper.updateUserByRoleId(permission);
                result = "已经修改该用户的权限";
            }
        }
        model.addAttribute("result", result);
        return "adminUpdateUserPerResult";
    }

    @RequestMapping("/adminUpdateStuInfoResult")
    public String adminUpdateStuInfoResult(
            UpdateStu updateStu, Model model) {
        String result = "";
        Integer updateStuUid = updateStu.getUserId();
        Integer updateStuTid = updateStu.getTaskId();
        if (updateStuUid == null || sysUserMapper.findUserByUserId(updateStuUid).getStatus() != 0) {
            result = "该学员不存在！！！";
        } else if (updateStuTid != null && taskMapper.findTaskById(updateStuTid) == null) {
            result = "该任务不存在";
        } else if (updateStuTid != null && !taskMapper.findTaskById(updateStuTid).getUserId().equals(updateStuUid)) {
            result = "该学员没有此任务,请重新确认填写信息是否正确";
        } else {
            String loginName = updateStu.getLogin_name();
            String userName = updateStu.getUser_name();
            String user_pwd = updateStu.getUser_pwd();
            Integer task_status = updateStu.getTask_status();
            String email = updateStu.getEmail();
            String tel = updateStu.getTel();

            Sys_user sys_user = new Sys_user();
            Sys_user sys_user1 = sysUserMapper.findUserByUserId(updateStuUid);


            sys_user.setUser_id(updateStuUid);
            if (email != "")
                sys_user.setEmail(email);
            else {
                sys_user.setEmail(sys_user1.getEmail());
            }

            if (loginName != "")
                sys_user.setLogin_name(loginName);
            else
                sys_user.setLogin_name(sys_user1.getLogin_name());

            if (user_pwd != "")
                sys_user.setLogin_pwd(user_pwd);
            else
                sys_user.setLogin_pwd(sys_user1.getLogin_pwd());

            if (userName != "")
                sys_user.setUser_name(userName);
            else
                sys_user.setUser_name(sys_user1.getUser_name());

            if (tel != "")
                sys_user.setTel(tel);
            else
                sys_user.setTel(sys_user1.getTel());

            sysUserMapper.updateUser(sys_user);
            result = "更新完成";

            if (updateStuTid != null) {
                Task task = new Task();
                task.setTaskId(updateStuTid);
                if (task_status != null) {
                    task.setStatus(task_status);
                } else {
                    task.setStatus(taskMapper.findTaskById(updateStuTid).getStatus());
                }
                taskMapper.updateTask(task);
            }else if(task_status!=null){
                result="请输入任务id";
            }
        }
        model.addAttribute("result", result);
        return "adminUpdateStuInfoResult";
    }

    @RequestMapping("/adminDelStudentResult")
    public String adminPermissionResult(DelStu delStu, Model model) {
        String result = "";
        Integer delStuId = delStu.getUser_id();
        if (sysUserMapper.findUserByUserId(delStuId) == null) {
            result = "不存在该学员信息，请重新输入！！！";
        } else {
            sysUserMapper.DeleteUserByUserId(delStu);
            if (delStu.getStatus() == 1)
                result = "成功删除该用户";
            else if (delStu.getStatus() == 2)
                result = "成功冻结该用户";
            else if (delStu.getStatus() == 0)
                result = "已还原该账号";
        }
        model.addAttribute("result", result);
        return "adminDelStudentResult";
    }

    @RequestMapping("/adminPublishTask")
    public String adminPublishTask(Model model) {
        SubmitTask submitTask = new SubmitTask();
        model.addAttribute("submitTask", submitTask);
        return "adminPublishTask";
    }

    @RequestMapping("/adminPublishTaskResult")
    public String adminPublishTaskResult(SubmitTask submitTask, Model model) {

        String result = "";
        if (submitTask.getType() == 1) {
            Edu_videosource video = eduVideoSourceMapper.findVideoById(submitTask.getVbid());
            if (video == null) result = "视频号有误，发布失败！";
        } else if (submitTask.getType() == 2) {
            Edu_booksource book = eduBooksourceMapper.findBookById(submitTask.getVbid());
            if (book == null) result = "书籍号有误，发布失败！";
        }

        List<Integer> usersId = new ArrayList<>();
        Integer num = -1;
        String ids = submitTask.getUsersId();
        for (int i = 0; i < ids.length(); i++) {
            char c = ids.charAt(i);
            if (c == ' ') {
                if (num != -1) {
                    usersId.add(num);
                    num = -1;
                }
            } else if (c - '0' >= 0 && c - '0' <= 9) {
                if (num == -1) num = c - '0';
                else num = num * 10 + (c - '0');
            } else result = "输入的用户id不合规范，发布失败！";
        }
        if (num != -1) usersId.add(num);

        if (result.equals("")) {
            for (int i = 0; i < usersId.size(); i++) {
                Integer id = usersId.get(i);
                Sys_user user = sysUserMapper.findUserByUserId(id);
                if (user == null) result = "用户id有误，发布失败！";
            }
        }

        if (result.equals("")) {

            for (int i = 0; i < usersId.size(); i++) {
                Task task = new Task();
                task.setStatus(0);
                task.setDetail(submitTask.getDetail());
                task.setType(submitTask.getType());
                task.setLevel(submitTask.getLevel());
                if (submitTask.getType() == 1) {
                    task.setVideoId(submitTask.getVbid());
                } else {
                    task.setBookId(submitTask.getVbid());
                }
                Timestamp now = new Timestamp(System.currentTimeMillis());
                task.setCreateTime(now);
                task.setUserId(usersId.get(i));
                //System.out.println(task);
                taskMapper.addTask(task);
            }
            result = "发布成功!";

        }

        model.addAttribute("result", result);
        return "adminPublishTaskResult";
    }

    @RequestMapping("/adminPublishExercise")
    public String adminPublishExercise(Model model) {
        Exercise exercise = new Exercise();
        model.addAttribute("exercise", exercise);
        return "adminPublishExercise";
    }

    @RequestMapping("/adminPublishExerciseResult")
    public String adminPublishExerciseResult(Exercise exercise, Model model) {
        String result = "";
        Integer vid = exercise.getVideoId();
        Edu_videosource video = eduVideoSourceMapper.findVideoById(vid);
        if (video == null) result = "输入视频号有误，发布失败！";
        if (result.equals("")) {
            exerciseMapper.addExercise(exercise);
            result = "发布成功！";

        }
        model.addAttribute("result", result);
        return "adminPublishExerciseResult";
    }

    @RequestMapping("/adminAddColleage")
    public String adminAddColleage(Model model) {
        Colleage colleage = new Colleage();
        model.addAttribute("colleage", colleage);
        return "adminAddColleage";
    }

    @RequestMapping("/adminAddColleageResult")
    public String adminAddColleageResult(Colleage colleage, Model model) {
        String result = "";
        if (colleage.getLongitude() > 136 || colleage.getLongitude() < 73 || colleage.getLatitude() > 70 || colleage.getLatitude() < 3) {
            result = "经纬度不在中国范围内，新增失败！";
        } else {
            colleageMapper.addColleage(colleage);
            result = "新增成功！";
        }
        model.addAttribute("result", result);
        return "adminAddColleageResult";
    }

    @RequestMapping("/adminAddStudio")
    public String adminAddStudio(Model model) {
        Edu_studio studio = new Edu_studio();
        model.addAttribute("studio", studio);
        return "adminAddStudio";
    }

    @RequestMapping("/adminAddStudioResult")
    public String adminAddStudioResult(Edu_studio studio, Model model) {
        String result = "";
        Integer cid = studio.getColleageId();
        Colleage colleage = colleageMapper.findColleageByColleageId(cid);
        List<Edu_studio> edu_studio = eduStudioMapper.findStudioByColleageId(cid);
        if (colleage == null || edu_studio.size() >= 4) result = "新增失败！请查看学院数量以及学院id是否有误";
        else {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            studio.setCreate_time(now);
            String con = studio.getContent();
            StringBuffer sb = new StringBuffer(con);
            int flag = 0;
            for (int i = 17; i < con.length(); i += 17) {
                if (flag == 0)
                    sb.insert(i, "</br>");
                else
                    sb.insert(i + flag * 5, "</br>");
                flag++;
            }
            studio.setContent(sb.toString());
            eduStudioMapper.addStudio(studio);
            result = "新增成功！";
        }
        model.addAttribute("result", result);
        return "adminAddStudioResult";
    }

    @RequestMapping("/adminAllTask")
    public String adminAllTask(Model model) {
        List<Task> taskes = taskMapper.findAllTask();
        List<Task> tasks = new ArrayList<>();
        for (Task t : taskes) {
            Integer uid = t.getUserId();
            if (sysUserMapper.findUserByUserId(uid).getStatus() == 0)
                tasks.add(t);
        }
        model.addAttribute("tasks", tasks);
        return "adminAllTask";
    }

    @RequestMapping("/adminAllExercise")
    public String adminAllExercise(Model model) {
        List<Exercise> exercises = exerciseMapper.findAllExercise();
        model.addAttribute("exercises", exercises);
        return "adminAllExercise";
    }

    @RequestMapping("/adminAllVedio")
    public String adminAllVedio(Model model) {
        List<Edu_videosource> videos = eduVideoSourceMapper.findAllVideo();
        model.addAttribute("videos", videos);
        return "adminAllVedio";
    }

    @RequestMapping("/adminAllBook")
    public String adminAllBook(Model model) {
        List<Edu_booksource> books = eduBooksourceMapper.findAllBook();
        model.addAttribute("books", books);
        return "adminAllBook";
    }

    @RequestMapping("/adminAllColleage")
    public String adminAllColleage(Model model) {
        List<Colleage> colleages = colleageMapper.findAllColleage();
        model.addAttribute("colleages", colleages);
        return "adminAllColleage";
    }

    @RequestMapping("/adminAllStudio")
    public String adminAllStudio(Model model) {
        List<Edu_studio> studios = eduStudioMapper.findAllStudio();
        model.addAttribute("studios", studios);
        return "adminAllStudio";
    }


    @RequestMapping("/adminAllUsers")
    public String adminAllUsers(Model model) {
        List<Sys_user> users = sysUserMapper.findAllUser();
        model.addAttribute("users", users);
        return "adminAllUsers";
    }


    //新旧分界线！！！！！！！！！！！！！！！！！！！

    @RequestMapping("/toPublishExercise")
    public String toPublishExercise(Model model) {
        Exercise e = new Exercise();
        model.addAttribute("e", e);
        return "publishExercise";
    }

    @RequestMapping("/toPublishTask")
    public String toPublishTask(Model model) {
        Task task = new Task();
        model.addAttribute("task", task);
        return "publishTask";
    }

    @RequestMapping("/publishExercise")
    public String publishExercise(Exercise e, Model model) {
        String msg = "";
        Integer vid = e.getVideoId();
        String content = e.getContent();
        if (vid == null || content.equals("")) msg = "视频号或习题内容为空，发布失败";
        else {
            Edu_videosource video = eduVideoSourceMapper.findVideoById(vid);
            if (video == null) msg = "视频号有误，发布失败";
            else {
                exerciseMapper.addExercise(e);
                msg = "发布成功";
            }
        }

        model.addAttribute("msg", msg);
        return "adminSuccess";
    }

    @RequestMapping("/publishTask")
    public String publishTask(Task task, Model model) {
        String msg = "";
        if (task.getType() == 1) {
            Edu_videosource video = eduVideoSourceMapper.findVideoById(task.getVideoId());
            if (video == null) msg = "视频号有误，发布失败！";
        } else if (task.getType() == 2) {
            Edu_booksource book = eduBooksourceMapper.findBookById(task.getBookId());
            if (book == null) msg = "书籍号有误，发布失败！";
        } else msg = "类型输入有误，发布失败！";
        if (task.getLevel() < 1 || task.getLevel() > 10) msg = "难度级别输入有误，发布失败！";
        Sys_user user = sysUserMapper.findUserByUserId(task.getUserId());
        if (user == null) msg = "用户id有误，发布失败！";
        if (msg.equals("")) {
            task.setStatus(0);
            taskMapper.addTask(task);
            msg = "发布成功！";
        }
        model.addAttribute("msg", msg);
        return "adminSuccess";
    }


}
