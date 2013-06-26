package com.scjinruan.policeofficer.deill.server.netty;

import org.jboss.netty.channel.MessageEvent;

import com.scjinruan.policeofficer.deill.core.Action;
import com.scjinruan.policeofficer.deill.core.Bunlde;
import com.scjinruan.policeofficer.deill.core.RequestService;

public class TCPRequestService implements RequestService {
	private Action action;
	private Bunlde bunlde;
	private MessageEvent event;
	public TCPRequestService(Action action,Bunlde bunlde,MessageEvent e){
		this.action=action;
		this.bunlde=bunlde;
		this.event=e;
	}
	
	@Override
	public void doService() {
		Bunlde request=action.execute(bunlde);
		if(request!=null){
			event.getChannel().write(request.getClientMessage());			
		}
	}

}
