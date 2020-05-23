package com.itembankmanagement.dao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name="title")
public class Title {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private String answer;

    private Date createDate;

    @Column(nullable = false)
    private Integer selectNum;

    @Column(nullable = false)
    private Float score;

   @Transient
   private Long typeId;

    @JoinColumn(name = "typeId")
    @ManyToOne(targetEntity = TitleType.class)
    private TitleType titleType;

    @Transient
    private Long chapterId;

    @JoinColumn(name = "chapterId")
    @ManyToOne(targetEntity = Chapter.class)
    private Chapter chapter;


    @Override
    public String toString() {
        return "Title{" +
                "id=" + id +
                ", content='" + content + '\'' +
                ", answer='" + answer + '\'' +
                ", createDate=" + createDate +
                ", selectNum=" + selectNum +
                ", typeId=" + typeId +
                ", chapterId=" + chapterId +
                '}';
    }
}
