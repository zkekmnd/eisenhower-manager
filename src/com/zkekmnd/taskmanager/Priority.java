package com.zkekmnd.taskmanager;

public enum Priority {
    DO(true, true),
    SCHEDULE(false, true),
    DELEGATE(true, false),
    ELIMINATE(false, false);

    private boolean urgent;
    private boolean important;

    Priority(boolean urgent, boolean important) {
        this.urgent = urgent;
        this.important = important;
    }

    public boolean isUrgent() {
        return urgent;
    }
    public boolean isImportant() {
        return important;
    }
    public String getLabel(){
        return switch(this){
            case Priority.DO -> "Do These First";
            case Priority.SCHEDULE -> "Schedule For Another Time";
            case Priority.DELEGATE -> "Deal When There Is Nothing Else To Do";
            case Priority.ELIMINATE -> "Set Aside For Now";
        };
    }
}