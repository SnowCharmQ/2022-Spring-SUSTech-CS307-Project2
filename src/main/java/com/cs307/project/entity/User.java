package com.cs307.project.entity;

import java.util.Objects;

public class User {
    String username;
    String pwd;
    String salt;
    boolean isSuper;
    boolean canInsert;
    boolean canDelete;
    boolean canUpdate;
    boolean canSelect;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isSuper() {
        return isSuper;
    }

    public void setSuper(boolean aSuper) {
        isSuper = aSuper;
    }

    public boolean isCanInsert() {
        return canInsert;
    }

    public void setCanInsert(boolean canInsert) {
        this.canInsert = canInsert;
    }

    public boolean isCanDelete() {
        return canDelete;
    }

    public void setCanDelete(boolean canDelete) {
        this.canDelete = canDelete;
    }

    public boolean isCanUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

    public boolean isCanSelect() {
        return canSelect;
    }

    public void setCanSelect(boolean canSelect) {
        this.canSelect = canSelect;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return isSuper == user.isSuper && canInsert == user.canInsert && canDelete == user.canDelete && canUpdate == user.canUpdate && canSelect == user.canSelect && Objects.equals(username, user.username) && Objects.equals(pwd, user.pwd) && Objects.equals(salt, user.salt);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, pwd, salt, isSuper, canInsert, canDelete, canUpdate, canSelect);
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                ", salt='" + salt + '\'' +
                ", isSuper=" + isSuper +
                ", canInsert=" + canInsert +
                ", canDelete=" + canDelete +
                ", canUpdate=" + canUpdate +
                ", canSelect=" + canSelect +
                '}';
    }
}
