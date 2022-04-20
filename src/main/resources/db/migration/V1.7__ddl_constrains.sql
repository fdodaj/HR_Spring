
alter table `user_department` add constraint `user_department_mtm` foreign key (`user_id`) references `user` (`id`);
alter table `user_department` add constraint `user_department_mtom` foreign key  (`department_id`) references `department` (`id`);

ALTER TABLE request add CONSTRAINT `user_request_fk` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE user ADD CONSTRAINT `department_user_fk` FOREIGN KEY (`id`) REFERENCES `department` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
-- ALTER TABLE user ADD CONSTRAINT `user_role_fk` FOREIGN KEY (`id`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `user` ADD CONSTRAINT `user_role_fk` FOREIGN KEY (`role`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
