package com.wipro.digital.assignment.web.crawler.actors;

import com.wipro.digital.assignment.web.crawler.bean.JobInformation;
import com.wipro.digital.assignment.web.crawler.jdbc.JobStore;

import akka.actor.UntypedAbstractActor;
import scala.Tuple2;

public class JobActor extends UntypedAbstractActor {

	@SuppressWarnings("unchecked")
	@Override
	public void onReceive(final Object message) {
		if (message instanceof Tuple2) {
			Tuple2<String, JobInformation> actionEvent = (Tuple2<String, JobInformation>) message;

			switch (actionEvent._1) {

			case "UPDATE":
				JobStore.updateJobInfo(actionEvent._2);
				break;

			default:
				break;
			}

		}
	}
}
