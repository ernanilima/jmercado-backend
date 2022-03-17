package br.com.ernanilima.jmercadobackend.service.logging;

import br.com.ernanilima.jmercadobackend.domain.IEntityLog;

import java.sql.Timestamp;

public class LogToEntity {

    /**
     * Logs para quando for inserir
     * @param iEntityLog IEntityLog
     */
    public static void toInsert(IEntityLog iEntityLog) {
        if (iEntityLog != null) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            iEntityLog.setDateInsert(now);
            iEntityLog.setDateUpdate(now);
        }
    }

    /**
     * Logs para quando for alterar
     * @param oldEntity IEntityLog
     * @param newEntity IEntityLog
     */
    public static void toUpdate(IEntityLog oldEntity, IEntityLog newEntity) {
        if (oldEntity != null) {
            Timestamp now = new Timestamp(System.currentTimeMillis());
            newEntity.setDateInsert(oldEntity.getDateInsert());
            newEntity.setDateUpdate(now);
        }
    }

    /**
     * Nao realiza alteracoes nos logs
     * @param oldEntity IEntityLog
     * @param newEntity IEntityLog
     */
    public static void dontUpdate(IEntityLog oldEntity, IEntityLog newEntity) {
        if (oldEntity != null) {
            newEntity.setDateInsert(oldEntity.getDateInsert());
            newEntity.setDateUpdate(oldEntity.getDateUpdate());
        }
    }
}
