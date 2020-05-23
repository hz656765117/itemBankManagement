package com.itembankmanagement.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "type")
public class TitleType {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50,nullable =false)
    private String name;

    @OneToMany(mappedBy = "titleType")
    private List<Title> titleList;

    @Override
    public String toString() {
        return "TitleType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", titleList=" + titleList +
                '}';
    }
}
