package br.com.ernanilima.jmercadobackend.domain;

import java.sql.Timestamp;

/**
 * Para entidades com log na tabela
 */
public interface IEntityLog {
    Timestamp getDateInsert();
    void setDateInsert(Timestamp date);

    Timestamp getDateUpdate();
    void setDateUpdate(Timestamp date);

    Timestamp getDateDelete();
    void setDateDelete(Timestamp date);
}
