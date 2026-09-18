CREATE DATABASE `Alumno`;
USE `Alumno`;

CREATE TABLE `Alumnos` (
    `Id` INT AUTO_INCREMENT NOT NULL,
    `Nombre` VARCHAR(45) DEFAULT NULL,
    `Edad` NUMERIC DEFAULT NULL,
    PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

select count(*) from Alumnos;
select avg(Edad) from Alumnos;