package com.efun.core.mapper.support;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.transaction.TransactionDefinition;

public class DataSourceTransactionManager extends org.springframework.jdbc.datasource.DataSourceTransactionManager {

	protected final Logger logger = LogManager.getLogger(this.getClass());

	@Override
	protected void doBegin(Object transaction, TransactionDefinition definition) {
		logger.debug("-->Transaction begin!");
		super.doBegin(transaction, definition);
	}

	@Override
	protected void doCleanupAfterCompletion(Object transaction) {
		super.doCleanupAfterCompletion(transaction);
		logger.debug("<--Transaction end!");
	}

}
