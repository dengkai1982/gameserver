package com.scjinruan.policeofficer.deill.server.netty;
import org.jboss.netty.channel.MessageEvent;

import com.scjinruan.policeofficer.deill.core.Action;
import com.scjinruan.policeofficer.deill.core.Bunlde;
import com.scjinruan.policeofficer.deill.core.RequestService;
public class UDPRequestService implements RequestService{
	private Action action;
	private Bunlde bunlde;
	private MessageEvent event;
	protected UDPRequestService(Action action, Bunlde bunlde, MessageEvent event) {
		this.action=action;
		this.bunlde=bunlde;
		this.event=event;
	}

	@Override
	public void doService() {
		Bunlde response=action.execute(bunlde);
		if(response!=null){
			event.getChannel().write(bunlde, event.getRemoteAddress());
		}
	}
}
