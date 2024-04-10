/**
 * (c) 2003-2018 MuleSoft, Inc. This software is protected under international copyright law. All use of this software is subject to
 * MuleSoft's Master Subscription Agreement (or other Terms of Service) separately entered into between you and MuleSoft. If such an
 * agreement is not in place, you may not use the software.
 */
package org.mule.extension.jsonlogger.internal.destinations.amq.client;

//import com.google.common.base.Preconditions;
import org.mule.runtime.api.scheduler.Scheduler;
import org.mule.runtime.http.api.client.HttpClient;

import com.mulesoft.mq.restclient.AnypointMQClientFactory;
import com.mulesoft.mq.restclient.AnypointMqClient;
import com.mulesoft.mq.restclient.DefaultAnypointMqClient;
import com.mulesoft.mq.restclient.client.mq.domain.CourierAuthenticationCredentials;
import com.mulesoft.mq.restclient.client.mq.domain.OAuthCredentials;

public class MuleBasedAnypointMQClientFactory implements AnypointMQClientFactory {

    private final HttpClient httpClient;
    private final Scheduler scheduler;

    public MuleBasedAnypointMQClientFactory(HttpClient httpClient, Scheduler scheduler) {
        this.httpClient = httpClient;
        this.scheduler = scheduler;
    }

    @Override
    public AnypointMqClient createClient(String courierApiUrl, CourierAuthenticationCredentials authenticationCredentials,
                                         String userAgentInfo) {
//        Preconditions.checkArgument(authenticationCredentials instanceof OAuthCredentials);
        return new DefaultAnypointMqClient(new AsyncMuleCourierRestClient(courierApiUrl, OAuthCredentials.class.cast(authenticationCredentials),
                                                                          userAgentInfo, httpClient, scheduler));
    }

}
