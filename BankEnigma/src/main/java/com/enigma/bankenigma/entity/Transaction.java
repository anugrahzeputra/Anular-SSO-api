package com.enigma.bankenigma.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "tx_user_saving")
public class Transaction {

    @Id
    @GeneratedValue(generator = "tx_uuid")
    @GenericGenerator(name = "tx_uuid", strategy = "uuid")
    private String id;
    private Timestamp timestamp;
    private String type;
    private String source;
    private String destination;
    private Integer total;

    public Transaction() {
    }

    public String getId() {
        return id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public String getType() {
        return type;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public void setType(String type) {
        this.type = type;
    }
}
