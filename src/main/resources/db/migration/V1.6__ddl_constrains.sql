
-- ALTER TABLE request add CONSTRAINT `user` FOREIGN KEY (`id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE NO ACTION;
ALTER TABLE `user` ADD CONSTRAINT `user_role` FOREIGN KEY (`user_role`) REFERENCES `role` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE `user` ADD CONSTRAINT `user_department` foreign key (`user_department`) references `department` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION;