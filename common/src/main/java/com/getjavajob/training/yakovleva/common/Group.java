package com.getjavajob.training.yakovleva.common;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "group_social")
public class Group implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "group_id")
    private int groupId;
    @Column(name = "group_name")
    private String groupName;
    @Column(name = "logo")
    private String logo;
    @Column(name = "info")
    private String info;
    @Column(name = "id_group_creator")
    private int idGroupCreator;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getIdGroupCreator() {
        return idGroupCreator;
    }

    public void setIdGroupCreator(int idGroupCreator) {
        this.idGroupCreator = idGroupCreator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return groupId == group.groupId &&
                idGroupCreator == group.idGroupCreator &&
                groupName.equals(group.groupName) &&
                logo.equals(group.logo) &&
                info.equals(group.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupId, groupName, logo, info, idGroupCreator);
    }

    @Override
    public String toString() {
        return "Group{" +
                "groupId=" + groupId +
                ", groupName='" + groupName + '\'' +
                ", logo='" + logo + '\'' +
                ", info='" + info + '\'' +
                ", idGroupCreator=" + idGroupCreator +
                '}';
    }
}