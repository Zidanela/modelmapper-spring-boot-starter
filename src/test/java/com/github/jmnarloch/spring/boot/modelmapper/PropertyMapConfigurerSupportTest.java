/**
 * Copyright (c) 2015 the original author or authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jmnarloch.spring.boot.modelmapper;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.springframework.util.Assert.notNull;

/**
 * Tests the registration of {@link ModelMapper}.
 *
 * @author Jakub Narloch
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PropertyMapConfigurerSupportTest.Application.class)
public class PropertyMapConfigurerSupportTest {

    @Autowired
    private ModelMapper modelMapper;

    @Test
    public void shouldInstantiateMapper() {

        notNull(modelMapper, "Model mapper hasn't been created.");
    }

    @Test
    public void shouldMapUserEntity() {

        // given
        final User user = new User("John Doe");

        // when
        final UserDTO result = modelMapper.map(user, UserDTO.class);

        // then
        Assert.assertEquals(user.getName(), result.getFirstName());
        Assert.assertEquals(user.getName(), result.getLastName());
    }

    @EnableAutoConfiguration
    public static class Application {

        @Bean
        public PropertyMapConfigurerSupport<User, UserDTO> userMapping() {
            return new PropertyMapConfigurerSupport<User, UserDTO>() {
                @Override
                public PropertyMap<User, UserDTO> mapping() {
                    return new PropertyMap<User, UserDTO>() {
                        @Override
                        protected void configure() {
                            map().setFirstName(source.getName());
                            map().setLastName(source.getName());
                        }
                    };
                }
            };
        }
    }

    public static class User {

        private String name;

        public User() {
        }

        public User(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class UserDTO {

        private String firstName;

        private String lastName;

        public UserDTO() {
        }

        public UserDTO(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }
    }
}