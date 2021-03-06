/*
* The MIT License (MIT)
*
* Copyright (c) 2017 Playtika
*
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:
*
* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.
*
* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
 */
package com.playtika.test.couchbase.springdata;

import com.couchbase.client.java.AsyncBucket;
import com.couchbase.client.java.Bucket;
import com.couchbase.client.java.env.CouchbaseEnvironment;
import com.couchbase.client.java.env.DefaultCouchbaseEnvironment;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.couchbase.config.AbstractCouchbaseConfiguration;

import java.util.List;

import static java.util.Arrays.asList;

@Configuration
@AllArgsConstructor
@EnableAutoConfiguration
@EnableConfigurationProperties(CouchbaseConfigurationProperties.class)
public class CouchbaseConfiguration extends AbstractCouchbaseConfiguration {

    private final CouchbaseConfigurationProperties couchbaseConfigurationProperties;

    @Override
    protected List<String> getBootstrapHosts() {
        return asList(couchbaseConfigurationProperties.getHosts().split(","));
    }

    @Override
    protected String getBucketName() {
        return couchbaseConfigurationProperties.getBucket();
    }

    @Override
    protected String getBucketPassword() {
        return couchbaseConfigurationProperties.getPassword();
    }

    @Override
    protected CouchbaseEnvironment getEnvironment() {
        return DefaultCouchbaseEnvironment
                .builder()
                .bootstrapHttpDirectPort(couchbaseConfigurationProperties.getBootstrapHttpDirectPort())
                .bootstrapCarrierDirectPort(couchbaseConfigurationProperties.getBootstrapCarrierDirectPort())
                .build();
    }

    @Bean
    AsyncBucket asyncCouchbaseBucket(Bucket bucket) {
        return bucket.async();
    }
}
