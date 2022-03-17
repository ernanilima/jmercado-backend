package br.com.ernanilima.jmercadobackend.domain;

import java.sql.Timestamp;

/**
 * Para entidades com log na tabela
 */
public interface IEntityLog {
    public Timestamp getDateInsert();
    public void setDateInsert(Timestamp dateInsert);

    public Timestamp getDateUpdate();
    public void setDateUpdate(Timestamp dateUpdate);
}
