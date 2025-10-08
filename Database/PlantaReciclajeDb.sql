create database PlantaReciclajeDB;
use PlantaReciclajeDB;

create table Materiales(
    idMaterial int primary key auto_increment,
    tipoMaterial varchar(50) not null,
    precioPorKg decimal(10,2) not null,
    constraint PK_Materiales primary key (idMaterial)
);

create table Administradores(
    idAdministrador int primary key auto_increment,
    nombre varchar(100) not null,
    correoElectronico varchar(100) not null unique,
    contrasena varchar(100) not null,
    constraint PK_Administradores primary key (idAdministrador)
);

create table Entregas(
    idEntrega int primary key auto_increment,
    idMaterial int not null,
    cantidadKg decimal(10,2) not null,
    fechaEntrega date not null,
    nombreProveedor varchar(100) not null,
    compensacion decimal(10,2) not null,
    constraint PK_Entregas primary key (idEntrega),
    constraint FK_Entregas_Materiales foreign key (idMaterial) references Materiales(idMaterial)
);
