package com.tiangua.zhz.model;

import java.util.Date;

/**
 * Created by adamFeng on 2016/1/20.
 * 留存任务
 */
public class ActiveTaskMoel {

    private int taskId;//任务id
    private int taskScore;//此任务可获取的元宝数
    private int taskDay; //此任务几天后可以开始
    private Date taskStart;//任务开始的事件(即第一次安装的时间)
    private int state;//留存任务完成状态

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getTaskDay() {
        return taskDay;
    }

    public void setTaskDay(int taskDay) {
        this.taskDay = taskDay;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public int getTaskScore() {
        return taskScore;
    }

    public void setTaskScore(int taskScore) {
        this.taskScore = taskScore;
    }

    public Date getTaskStart() {
        return taskStart;
    }

    public void setTaskStart(Date taskStart) {
        this.taskStart = taskStart;
    }
}
