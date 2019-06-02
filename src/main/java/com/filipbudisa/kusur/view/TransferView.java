package com.filipbudisa.kusur.view;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.filipbudisa.kusur.model.Transfer;

public class TransferView extends TransactionView {

	@JsonProperty("from_user_id")
	private long fromUserId;

	@JsonProperty("to_user_id")
	private long toUserId;

	public TransferView(Transfer transfer){
		super(transfer);

		this.fromUserId = transfer.getFrom().getId();
		this.toUserId = transfer.getTo().getId();
	}

	public long getFromUserId(){
		return fromUserId;
	}

	public long getToUserId(){
		return toUserId;
	}
}
