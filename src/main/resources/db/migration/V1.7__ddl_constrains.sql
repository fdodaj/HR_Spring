alter table `user`  add constraint `user_role_fk` foreign key (`user_role`) references  `role` (`id`);
alter table  `request_permission` add constraint `user_request_fk` foreign key (`userEntity`) references  `user` (`id`);
alter table  `department` add constraint `department_request_fk` foreign key (`department_leader`) references  `user` (`id`);

alter table `user_department` add constraint `user_department_mtm` foreign key (`user_id`) references `user` (`id`);
alter table `user_department` add constraint `user_department_mtom` foreign key  (`department_id`) references `department` (`id`);