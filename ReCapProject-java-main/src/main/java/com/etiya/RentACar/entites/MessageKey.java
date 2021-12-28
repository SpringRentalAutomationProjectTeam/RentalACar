package com.etiya.RentACar.entites;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="message_keys")
public class MessageKey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "key")
    private String key;
    @OneToMany(mappedBy = "messageKey")
    private List<LanguageWord> languageWords;


}
