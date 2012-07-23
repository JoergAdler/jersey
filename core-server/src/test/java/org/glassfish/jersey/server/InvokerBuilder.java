/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2011-2012 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * http://glassfish.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */
package org.glassfish.jersey.server;

import javax.inject.Inject;
import javax.inject.Provider;

import org.glassfish.jersey.process.internal.RequestInvoker;
import org.glassfish.jersey.process.internal.Stages;
import org.glassfish.jersey.server.internal.routing.RoutedInflectorExtractorStage;
import org.glassfish.jersey.server.internal.routing.Router;
import org.glassfish.jersey.server.internal.routing.RoutingStage;

/**
 * Test utility binder for testing hierarchical request accepting (i.e. resource matching).
 *
 * @author Marek Potociar (marek.potociar at oracle.com)
 */
public class InvokerBuilder {

    @Inject
    private Provider<RoutingStage.Builder> matchingStageFactory;
    @Inject
    private Provider<RoutedInflectorExtractorStage> inflectorExtractingStageFactory;
    @Inject
    private ReferencesInitializer referencesInitializer;
    @Inject
    private ServerBinder.RequestInvokerBuilder invokerBuilder;


    /**
     * Build a request processor using the resource matching acceptor
     * for testing purposes.
     *
     * @param matchingRoot root resource matching acceptor.
     * @return request processor.
     */
    public RequestInvoker<ContainerRequest, ContainerResponse> build(final Router matchingRoot) {

        return invokerBuilder.build(Stages
                .chain(referencesInitializer)
                .to(matchingStageFactory.get().build(matchingRoot))
                .build(inflectorExtractingStageFactory.get()));
    }
}