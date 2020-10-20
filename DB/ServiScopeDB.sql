-- phpMyAdmin SQL Dump
-- version 4.8.0.1
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 20-10-2020 a las 17:35:19
-- Versión del servidor: 10.1.32-MariaDB
-- Versión de PHP: 7.2.5

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `ServiscopeDB`
--
CREATE DATABASE IF NOT EXISTS `ServiscopeDB` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `ServiscopeDB`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `certificaciones`
--

CREATE TABLE `certificaciones` (
  `id_certificacion` int(11) NOT NULL,
  `id_tecnico` int(11) NOT NULL,
  `descripcion` text NOT NULL,
  `fecha_ingreso` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comunas`
--

CREATE TABLE `comunas` (
  `id_comuna` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `id_region` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `comunas`
--

INSERT INTO `comunas` (`id_comuna`, `nombre`, `id_region`) VALUES
(1, 'Arica', 1),
(2, 'Camarones', 1),
(3, 'General Lagos', 2),
(4, 'Putre', 2),
(5, 'Alto Hospicio', 0),
(6, 'Iquique', 0),
(7, 'Camiña', 0),
(8, 'Colchane', 0),
(9, 'Huara', 0),
(10, 'Pica', 0),
(11, 'Pozo Almonte', 0),
(12, 'Antofagasta', 0),
(13, 'Mejillones', 0),
(14, 'Sierra Gorda', 0),
(15, 'Taltal', 0),
(16, 'Calama', 0),
(17, 'Ollague', 0),
(18, 'San Pedro de Atacama', 0),
(19, 'María Elena', 0),
(20, 'Tocopilla', 0),
(21, 'Chañaral', 0),
(22, 'Diego de Almagro', 0),
(23, 'Caldera', 0),
(24, 'Copiapó', 0),
(26, 'Alto del Carmen', 0),
(27, 'Freirina', 0),
(28, 'Huasco', 0),
(29, 'Vallenar', 0),
(30, 'Canela', 0),
(31, 'Illapel', 0),
(32, 'Los Vilos', 0),
(33, 'Salamanca', 0),
(34, 'Andacollo', 0),
(35, 'Coquimbo', 0),
(36, 'La Higuera', 0),
(37, 'La Serena', 0),
(38, 'Paihuaco', 0),
(39, 'Vicuña', 0),
(40, 'Combarbalá', 0),
(41, 'Monte Patria', 0),
(42, 'Ovalle', 0),
(43, 'Punitaqui', 0),
(44, 'Río Hurtado', 0),
(45, 'Isla de Pascua', 0),
(46, 'Calle Larga', 0),
(47, 'Los Andes', 0),
(48, 'Rinconada', 0),
(49, 'San Esteban', 0),
(50, 'La Ligua', 0),
(51, 'Papudo', 0),
(52, 'Petorca', 0),
(53, 'Zapallar', 0),
(54, 'Hijuelas', 0),
(55, 'La Calera', 0),
(56, 'La Cruz', 0),
(57, 'Limache', 0),
(58, 'Nogales', 0),
(59, 'Olmué', 0),
(60, 'Quillota', 0),
(61, 'Algarrobo', 0),
(62, 'Cartagena', 0),
(63, 'El Quisco', 0),
(64, 'El Tabo', 0),
(65, 'San Antonio', 0),
(66, 'Santo Domingo', 0),
(67, 'Catemu', 0),
(68, 'Llaillay', 0),
(69, 'Panquehue', 0),
(70, 'Putaendo', 0),
(71, 'San Felipe', 0),
(72, 'Santa María', 0),
(73, 'Casablanca', 0),
(74, 'Concón', 0),
(75, 'Juan Fernández', 0),
(76, 'Puchuncaví', 0),
(77, 'Quilpué', 0),
(78, 'Quintero', 0),
(79, 'Valparaíso', 0),
(80, 'Villa Alemana', 0),
(81, 'Viña del Mar', 0),
(82, 'Colina', 7),
(83, 'Lampa', 7),
(84, 'Tiltil', 7),
(85, 'Pirque', 7),
(86, 'Puente Alto', 7),
(87, 'San José de Maipo', 7),
(88, 'Buin', 7),
(89, 'Calera de Tango', 7),
(90, 'Paine', 7),
(91, 'San Bernardo', 7),
(92, 'Alhué', 7),
(93, 'Curacaví', 7),
(94, 'María Pinto', 7),
(95, 'Melipilla', 7),
(96, 'San Pedro', 7),
(97, 'Cerrillos', 7),
(98, 'Cerro Navia', 7),
(99, 'Conchalí', 7),
(100, 'El Bosque', 7),
(101, 'Estación Central', 7),
(102, 'Huechuraba', 7),
(103, 'Independencia', 7),
(104, 'La Cisterna', 7),
(105, 'La Granja', 7),
(106, 'La Florida', 7),
(107, 'La Pintana', 7),
(108, 'La Reina', 7),
(109, 'Las Condes', 7),
(110, 'Lo Barnechea', 7),
(111, 'Lo Espejo', 7),
(112, 'Lo Prado', 7),
(113, 'Macul', 7),
(114, 'Maipú', 7),
(115, 'Ñuñoa', 7),
(116, 'Pedro Aguirre Cerda', 7),
(117, 'Peñalolén', 7),
(118, 'Providencia', 7),
(119, 'Pudahuel', 7),
(120, 'Quilicura', 7),
(121, 'Quinta Normal', 7),
(122, 'Recoleta', 7),
(123, 'Renca', 7),
(124, 'San Miguel', 7),
(125, 'San Joaquín', 7),
(126, 'San Ramón', 7),
(127, 'Santiago', 7),
(128, 'Vitacura', 7),
(129, 'El Monte', 7),
(130, 'Isla de Maipo', 7),
(131, 'Padre Hurtado', 7),
(132, 'Peñaflor', 7),
(133, 'Talagante', 7),
(134, 'Codegua', 0),
(135, 'Coínco', 0),
(136, 'Coltauco', 0),
(137, 'Doñihue', 0),
(138, 'Graneros', 0),
(139, 'Las Cabras', 0),
(140, 'Machalí', 0),
(141, 'Malloa', 0),
(142, 'Mostazal', 0),
(143, 'Olivar', 0),
(144, 'Peumo', 0),
(145, 'Pichidegua', 0),
(146, 'Quinta de Tilcoco', 0),
(147, 'Rancagua', 0),
(148, 'Rengo', 0),
(149, 'Requínoa', 0),
(150, 'San Vicente de Tagua Tagua', 0),
(151, 'La Estrella', 0),
(152, 'Litueche', 0),
(153, 'Marchihue', 0),
(154, 'Navidad', 0),
(155, 'Peredones', 0),
(156, 'Pichilemu', 0),
(157, 'Chépica', 0),
(158, 'Chimbarongo', 0),
(159, 'Lolol', 0),
(160, 'Nancagua', 0),
(161, 'Palmilla', 0),
(162, 'Peralillo', 0),
(163, 'Placilla', 0),
(164, 'Pumanque', 0),
(165, 'San Fernando', 0),
(166, 'Santa Cruz', 0),
(167, 'Cauquenes', 0),
(168, 'Chanco', 0),
(169, 'Pelluhue', 0),
(170, 'Curicó', 0),
(171, 'Hualañé', 0),
(172, 'Licantén', 0),
(173, 'Molina', 0),
(174, 'Rauco', 0),
(175, 'Romeral', 0),
(176, 'Sagrada Familia', 0),
(177, 'Teno', 0),
(178, 'Vichuquén', 0),
(179, 'Colbún', 0),
(180, 'Linares', 0),
(181, 'Longaví', 0),
(182, 'Parral', 0),
(183, 'Retiro', 0),
(184, 'San Javier', 0),
(185, 'Villa Alegre', 0),
(186, 'Yerbas Buenas', 0),
(187, 'Constitución', 0),
(188, 'Curepto', 0),
(189, 'Empedrado', 0),
(190, 'Maule', 0),
(191, 'Pelarco', 0),
(192, 'Pencahue', 0),
(193, 'Río Claro', 0),
(194, 'San Clemente', 0),
(195, 'San Rafael', 0),
(196, 'Talca', 0),
(197, 'Arauco', 0),
(198, 'Cañete', 0),
(199, 'Contulmo', 0),
(200, 'Curanilahue', 0),
(201, 'Lebu', 0),
(202, 'Los Álamos', 0),
(203, 'Tirúa', 0),
(204, 'Alto Biobío', 0),
(205, 'Antuco', 0),
(206, 'Cabrero', 0),
(207, 'Laja', 0),
(208, 'Los Ángeles', 0),
(209, 'Mulchén', 0),
(210, 'Nacimiento', 0),
(211, 'Negrete', 0),
(212, 'Quilaco', 0),
(213, 'Quilleco', 0),
(214, 'San Rosendo', 0),
(215, 'Santa Bárbara', 0),
(216, 'Tucapel', 0),
(217, 'Yumbel', 0),
(218, 'Chiguayante', 0),
(219, 'Concepción', 0),
(220, 'Coronel', 0),
(221, 'Florida', 0),
(222, 'Hualpén', 0),
(223, 'Hualqui', 0),
(224, 'Lota', 0),
(225, 'Penco', 0),
(226, 'San Pedro de La Paz', 0),
(227, 'Santa Juana', 0),
(228, 'Talcahuano', 0),
(229, 'Tomé', 0),
(230, 'Bulnes', 0),
(231, 'Chillán', 0),
(232, 'Chillán Viejo', 0),
(233, 'Cobquecura', 0),
(234, 'Coelemu', 0),
(235, 'Coihueco', 0),
(236, 'El Carmen', 0),
(237, 'Ninhue', 0),
(238, 'Ñiquen', 0),
(239, 'Pemuco', 0),
(240, 'Pinto', 0),
(241, 'Portezuelo', 0),
(242, 'Quillón', 0),
(243, 'Quirihue', 0),
(244, 'Ránquil', 0),
(245, 'San Carlos', 0),
(246, 'San Fabián', 0),
(247, 'San Ignacio', 0),
(248, 'San Nicolás', 0),
(249, 'Treguaco', 0),
(250, 'Yungay', 0),
(251, 'Carahue', 0),
(252, 'Cholchol', 0),
(253, 'Cunco', 0),
(254, 'Curarrehue', 0),
(255, 'Freire', 0),
(256, 'Galvarino', 0),
(257, 'Gorbea', 0),
(258, 'Lautaro', 0),
(259, 'Loncoche', 0),
(260, 'Melipeuco', 0),
(261, 'Nueva Imperial', 0),
(262, 'Padre Las Casas', 0),
(263, 'Perquenco', 0),
(264, 'Pitrufquén', 0),
(265, 'Pucón', 0),
(266, 'Saavedra', 0),
(267, 'Temuco', 0),
(268, 'Teodoro Schmidt', 0),
(269, 'Toltén', 0),
(270, 'Vilcún', 0),
(271, 'Villarrica', 0),
(272, 'Angol', 0),
(273, 'Collipulli', 0),
(274, 'Curacautín', 0),
(275, 'Ercilla', 0),
(276, 'Lonquimay', 0),
(277, 'Los Sauces', 0),
(278, 'Lumaco', 0),
(279, 'Purén', 0),
(280, 'Renaico', 0),
(281, 'Traiguén', 0),
(282, 'Victoria', 0),
(283, 'Corral', 0),
(284, 'Lanco', 0),
(285, 'Los Lagos', 0),
(286, 'Máfil', 0),
(287, 'Mariquina', 0),
(288, 'Paillaco', 0),
(289, 'Panguipulli', 0),
(290, 'Valdivia', 0),
(291, 'Futrono', 0),
(292, 'La Unión', 0),
(293, 'Lago Ranco', 0),
(294, 'Río Bueno', 0),
(295, 'Ancud', 0),
(296, 'Castro', 0),
(297, 'Chonchi', 0),
(298, 'Curaco de Vélez', 0),
(299, 'Dalcahue', 0),
(300, 'Puqueldón', 0),
(301, 'Queilén', 0),
(302, 'Quemchi', 0),
(303, 'Quellón', 0),
(304, 'Quinchao', 0),
(305, 'Calbuco', 0),
(306, 'Cochamó', 0),
(307, 'Fresia', 0),
(308, 'Frutillar', 0),
(309, 'Llanquihue', 0),
(310, 'Los Muermos', 0),
(311, 'Maullín', 0),
(312, 'Puerto Montt', 0),
(313, 'Puerto Varas', 0),
(314, 'Osorno', 0),
(315, 'Puero Octay', 0),
(316, 'Purranque', 0),
(317, 'Puyehue', 0),
(318, 'Río Negro', 0),
(319, 'San Juan de la Costa', 0),
(320, 'San Pablo', 0),
(321, 'Chaitén', 0),
(322, 'Futaleufú', 0),
(323, 'Hualaihué', 0),
(324, 'Palena', 0),
(325, 'Aisén', 0),
(326, 'Cisnes', 0),
(327, 'Guaitecas', 0),
(328, 'Cochrane', 0),
(329, 'O\'higgins', 0),
(330, 'Tortel', 0),
(331, 'Coihaique', 0),
(332, 'Lago Verde', 0),
(333, 'Chile Chico', 0),
(334, 'Río Ibáñez', 0),
(335, 'Antártica', 0),
(336, 'Cabo de Hornos', 0),
(337, 'Laguna Blanca', 0),
(338, 'Punta Arenas', 0),
(339, 'Río Verde', 0),
(340, 'San Gregorio', 0),
(341, 'Porvenir', 0),
(342, 'Primavera', 0),
(343, 'Timaukel', 0),
(344, 'Natales', 0),
(345, 'Torres del Paine', 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estado_solicitud`
--

CREATE TABLE `estado_solicitud` (
  `id_estado_solicitud` int(1) NOT NULL,
  `descripción` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `estado_solicitud`
--

INSERT INTO `estado_solicitud` (`id_estado_solicitud`, `descripción`) VALUES
(0, 'En progreso'),
(1, 'Reprogramado'),
(2, 'Cancelado'),
(3, 'En espera'),
(4, 'Resolver');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `historial`
--

CREATE TABLE `historial` (
  `id_historial` int(11) NOT NULL,
  `id_solicitud` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_tecnico` int(11) DEFAULT NULL,
  `titulo` varchar(100) NOT NULL,
  `fecha_modificacion` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `comentarios` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `historial`
--

INSERT INTO `historial` (`id_historial`, `id_solicitud`, `id_usuario`, `id_tecnico`, `titulo`, `fecha_modificacion`, `comentarios`) VALUES
(1, 2, 1, 1, 'qwerfdas', '2020-10-20 14:52:46', 'ngfredwq'),
(2, 3, 1, NULL, 'Prueba', '2020-10-20 14:52:49', 'Solicitud creada'),
(73, 1145, 104, NULL, 'Nueva solicitud', '2020-10-20 15:32:34', 'Solicitud creada');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `lugar_trabajo`
--

CREATE TABLE `lugar_trabajo` (
  `id_lugardetrabajo` int(11) NOT NULL,
  `id_tecnico` int(11) NOT NULL,
  `id_region` int(11) NOT NULL,
  `id_comuna` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `lugar_trabajo`
--

INSERT INTO `lugar_trabajo` (`id_lugardetrabajo`, `id_tecnico`, `id_region`, `id_comuna`) VALUES
(1, 1, 7, 325),
(2, 1, 5, 26);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `pruebas`
--

CREATE TABLE `pruebas` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `correo` varchar(100) DEFAULT NULL,
  `contrasena` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `pruebas`
--

INSERT INTO `pruebas` (`id`, `nombre`, `correo`, `contrasena`) VALUES
(0, 'Hola', 'Prueba', 'Otra peura'),
(1, 'primero', 'primero@gmail.com', '123321'),
(2, 'Name', 'Name', 'Name');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `regiones`
--

CREATE TABLE `regiones` (
  `id_region` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `ordinal` varchar(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `regiones`
--

INSERT INTO `regiones` (`id_region`, `nombre`, `ordinal`) VALUES
(1, 'Arica y Parinacota', 'XV'),
(2, 'Tarapacá', 'I'),
(3, 'Antofagasta', 'II'),
(4, 'Atacama', 'III'),
(5, 'Coquimbo', 'IV'),
(6, 'Valparaiso', 'V'),
(7, 'Metropolitana de Santiago', 'RM'),
(8, 'Libertador General Bernardo O\'Higgins', 'VI'),
(9, 'Maule', 'VII'),
(10, 'Biobío', 'VIII'),
(11, 'La Araucanía', 'IX'),
(12, 'Los Ríos', 'XIV'),
(13, 'Los Lagos', 'X'),
(14, 'Aisén del General Carlos Ibáñez del Campo', 'XI'),
(15, 'Magallanes y de la Antártica Chilena', 'XII'),
(16, 'SELECCIONE UNA REGIÓN', '0');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `rol`
--

CREATE TABLE `rol` (
  `id_rol` int(1) NOT NULL,
  `nombre` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `rol`
--

INSERT INTO `rol` (`id_rol`, `nombre`) VALUES
(1, 'Cliente'),
(2, 'Especialista'),
(4, 'En Creación'),
(5, 'Visita');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `servicio`
--

CREATE TABLE `servicio` (
  `id_servicio` int(11) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `servicio`
--

INSERT INTO `servicio` (`id_servicio`, `nombre`, `descripcion`) VALUES
(1, 'Plomero', 'Descripción del servicio de plomeria'),
(2, 'Carpintero', 'Descripción del servicio de carpinteria'),
(3, 'Mudanza', 'Descripción del servicio de Mudanzas'),
(4, 'Albañil', 'Descripción del servicio de Albañileria'),
(5, 'Electricista', 'Descripción del servicio de Electricista'),
(6, 'Limpieza', 'Descripción del servicio de Limpieza'),
(7, 'Gas', 'Descripción del servicio de mantenimiento de Gas'),
(8, 'Arquitecto', 'Descripción del servicio de Arquitectura'),
(9, 'Mecanico', 'Descripción del servicio de Mecanica automotriz'),
(10, 'Belleza', 'Descripción del servicio de Belleza y sus derivados'),
(11, 'Bienestar', 'Descripción del servicio de Bienestar'),
(12, 'Cerrajero', 'Descripción del servicio de Cerrajeria'),
(13, 'Control de Plagas', 'Descripción del servicio de Control de Plagas'),
(14, 'Cuidador', 'Descripción del servicio de Cuidador'),
(15, 'Decorador', 'Descripción del servicio de Decorador'),
(16, 'Eventos', 'Descripción del servicio de gestión de Everntos'),
(17, 'Herrero', 'Descripción del servicio de Herreria'),
(18, 'Instalador', 'Descripción del servicio de Instalador'),
(19, 'Jardinero', 'Descripción del servicio de Jardineria'),
(20, 'Mascotas', 'Descripción del servicio relebante a las mascotas'),
(21, 'Mudanzas', 'Descripción del servicio de Mudanzas'),
(22, 'Pintor', 'Descripción del servicio de Pintura'),
(23, 'Piscinas', 'Descripción del servicio de Piscinas'),
(24, 'Seguridad', 'Descripción del servicio de Seguridad'),
(25, 'Tapicero', 'Descripción del servicio de Tepiceria'),
(26, 'Tecnico', 'Descripción del servicio de Técnico');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `solicitud`
--

CREATE TABLE `solicitud` (
  `id_solicitud` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_tecnico` int(11) DEFAULT NULL,
  `fecha` datetime NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP,
  `titulo` varchar(100) NOT NULL,
  `descripcion` text NOT NULL,
  `id_region` int(11) NOT NULL,
  `id_comuna` int(11) NOT NULL,
  `direccion` varchar(100) NOT NULL,
  `valor` int(100) NOT NULL DEFAULT '0',
  `id_servicio` int(11) NOT NULL,
  `estado_solicitud` int(1) NOT NULL DEFAULT '0',
  `valoracion` int(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `solicitud`
--

INSERT INTO `solicitud` (`id_solicitud`, `id_usuario`, `id_tecnico`, `fecha`, `titulo`, `descripcion`, `id_region`, `id_comuna`, `direccion`, `valor`, `id_servicio`, `estado_solicitud`, `valoracion`) VALUES
(2, 1, 1, '2020-10-20 14:51:09', 'Solicitud servicios 2', 'Prueba 1', 7, 34, 'mi casa', 0, 1, 1, 0),
(3, 1, 1, '2020-10-20 14:52:08', 'Arreglar mi cocina', 'Mi cocina presenta problemas', 7, 12, 'Saba ELtiti 850', 0, 2, 2, 0),
(4, 1, NULL, '2020-10-20 14:52:11', 'nueva solicitud', 'prueba', 7, 92, 'miksa', 0, 4, 3, 0),
(1145, 104, NULL, '2020-10-20 15:32:33', 'Nueva solicitud', 'Solicitud de Prueba', 7, 97, 'miksa', 0, 11, 3, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tecnico`
--

CREATE TABLE `tecnico` (
  `id_tecnico` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_region` int(11) NOT NULL,
  `id_comuna` int(11) NOT NULL,
  `direccion` varchar(100) NOT NULL,
  `lugar_trabajo` int(11) DEFAULT NULL,
  `id_servicio` int(11) NOT NULL,
  `descripcion` text NOT NULL,
  `valoracion` int(1) DEFAULT '0',
  `certifiacion` tinyint(1) DEFAULT NULL,
  `eliminado` tinyint(1) NOT NULL,
  `fecha_eliminado` timestamp NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcado de datos para la tabla `tecnico`
--

INSERT INTO `tecnico` (`id_tecnico`, `id_usuario`, `id_region`, `id_comuna`, `direccion`, `lugar_trabajo`, `id_servicio`, `descripcion`, `valoracion`, `certifiacion`, `eliminado`, `fecha_eliminado`) VALUES
(1, 1, 7, 89, 'miksa', 0, 4, 'Santiago', 1, 0, 0, '2020-09-19 01:03:33'),
(12, 104, 7, 100, 'los nogales 3348', NULL, 4, '4 años en el oficio', NULL, NULL, 0, '0000-00-00 00:00:00');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL,
  `nombres` varchar(100) CHARACTER SET latin1 NOT NULL,
  `apellidos` varchar(100) CHARACTER SET latin1 NOT NULL,
  `rut` int(9) NOT NULL,
  `email` varchar(100) CHARACTER SET latin1 NOT NULL,
  `region` int(11) NOT NULL,
  `comuna` int(11) NOT NULL,
  `direccion` varchar(100) CHARACTER SET latin1 NOT NULL,
  `telefono` int(50) NOT NULL,
  `contrasena` varchar(100) CHARACTER SET latin1 NOT NULL,
  `cod_contr` int(4) DEFAULT NULL,
  `fecha_registro` datetime NOT NULL,
  `tipo_usuario` int(1) NOT NULL,
  `eliminado` tinyint(1) NOT NULL,
  `fecha_eliminado` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `nombres`, `apellidos`, `rut`, `email`, `region`, `comuna`, `direccion`, `telefono`, `contrasena`, `cod_contr`, `fecha_registro`, `tipo_usuario`, `eliminado`, `fecha_eliminado`) VALUES
(1, 'Cliente', 'Tipo', 1, '1', 10, 1, 'Santiago', 1, '!Ul3CGlauV7J', NULL, '2020-09-19 01:03:33', 1, 0, '0000-00-00'),
(104, 'Sebastian', 'Fonseca', 198562440, 's.fonsecagutierrez@gmail.com', 7, 89, 'Puente Alto', 968510518, 'c20ad4d76fe97759aa27a0c99bff6710', NULL, '2020-10-20 15:01:16', 1, 0, NULL);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `certificaciones`
--
ALTER TABLE `certificaciones`
  ADD PRIMARY KEY (`id_certificacion`),
  ADD UNIQUE KEY `id_certificacion` (`id_certificacion`),
  ADD KEY `FK_id_tecnico` (`id_tecnico`);

--
-- Indices de la tabla `comunas`
--
ALTER TABLE `comunas`
  ADD PRIMARY KEY (`id_comuna`),
  ADD KEY `FK_id_region` (`id_region`);

--
-- Indices de la tabla `estado_solicitud`
--
ALTER TABLE `estado_solicitud`
  ADD PRIMARY KEY (`id_estado_solicitud`),
  ADD UNIQUE KEY `id_estado_solicitud` (`id_estado_solicitud`);

--
-- Indices de la tabla `historial`
--
ALTER TABLE `historial`
  ADD PRIMARY KEY (`id_historial`),
  ADD UNIQUE KEY `id_historial` (`id_historial`),
  ADD UNIQUE KEY `id_historial_2` (`id_historial`),
  ADD KEY `FK_id_solicitud` (`id_solicitud`),
  ADD KEY `FK_id_usuario` (`id_usuario`),
  ADD KEY `FK_id_tecnico` (`id_tecnico`);

--
-- Indices de la tabla `lugar_trabajo`
--
ALTER TABLE `lugar_trabajo`
  ADD PRIMARY KEY (`id_lugardetrabajo`),
  ADD UNIQUE KEY `id_lugardetrabajo` (`id_lugardetrabajo`),
  ADD UNIQUE KEY `FK_id_region` (`id_region`),
  ADD UNIQUE KEY `FK_id_comuna` (`id_comuna`) USING BTREE,
  ADD KEY `FK_id_tecnico` (`id_tecnico`);

--
-- Indices de la tabla `pruebas`
--
ALTER TABLE `pruebas`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `regiones`
--
ALTER TABLE `regiones`
  ADD PRIMARY KEY (`id_region`);

--
-- Indices de la tabla `rol`
--
ALTER TABLE `rol`
  ADD PRIMARY KEY (`id_rol`);

--
-- Indices de la tabla `servicio`
--
ALTER TABLE `servicio`
  ADD PRIMARY KEY (`id_servicio`),
  ADD UNIQUE KEY `id_servicio` (`id_servicio`),
  ADD UNIQUE KEY `nombre` (`nombre`);

--
-- Indices de la tabla `solicitud`
--
ALTER TABLE `solicitud`
  ADD PRIMARY KEY (`id_solicitud`),
  ADD UNIQUE KEY `id_solicitud` (`id_solicitud`),
  ADD KEY `FK_id_tecnico` (`id_tecnico`),
  ADD KEY `FK_id_servicio` (`id_servicio`) USING BTREE,
  ADD KEY `FK_estado_solicitud` (`estado_solicitud`),
  ADD KEY `FK_valoracion` (`valoracion`) USING BTREE,
  ADD KEY `FK_id_region` (`id_region`),
  ADD KEY `FK_id_comuna` (`id_comuna`),
  ADD KEY `FK_id_usuario` (`id_usuario`) USING BTREE;

--
-- Indices de la tabla `tecnico`
--
ALTER TABLE `tecnico`
  ADD PRIMARY KEY (`id_tecnico`),
  ADD UNIQUE KEY `id_tecnico` (`id_tecnico`),
  ADD KEY `FK_id_servicio` (`id_servicio`) USING BTREE,
  ADD KEY `FK_id_usuario` (`id_usuario`),
  ADD KEY `id_usuario` (`id_usuario`) USING BTREE;

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `rut` (`rut`),
  ADD UNIQUE KEY `telefono` (`telefono`),
  ADD UNIQUE KEY `email` (`email`),
  ADD UNIQUE KEY `FK_id_historial` (`id_usuario`),
  ADD KEY `FK_tipo_usuario` (`tipo_usuario`),
  ADD KEY `FK_id_region` (`region`) USING BTREE,
  ADD KEY `FK_id_comuna` (`comuna`) USING BTREE;

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `certificaciones`
--
ALTER TABLE `certificaciones`
  MODIFY `id_certificacion` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `comunas`
--
ALTER TABLE `comunas`
  MODIFY `id_comuna` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=346;

--
-- AUTO_INCREMENT de la tabla `historial`
--
ALTER TABLE `historial`
  MODIFY `id_historial` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=74;

--
-- AUTO_INCREMENT de la tabla `lugar_trabajo`
--
ALTER TABLE `lugar_trabajo`
  MODIFY `id_lugardetrabajo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT de la tabla `regiones`
--
ALTER TABLE `regiones`
  MODIFY `id_region` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT de la tabla `rol`
--
ALTER TABLE `rol`
  MODIFY `id_rol` int(1) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `servicio`
--
ALTER TABLE `servicio`
  MODIFY `id_servicio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

--
-- AUTO_INCREMENT de la tabla `solicitud`
--
ALTER TABLE `solicitud`
  MODIFY `id_solicitud` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1146;

--
-- AUTO_INCREMENT de la tabla `tecnico`
--
ALTER TABLE `tecnico`
  MODIFY `id_tecnico` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=105;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `certificaciones`
--
ALTER TABLE `certificaciones`
  ADD CONSTRAINT `certificaciones_ibfk_1` FOREIGN KEY (`id_tecnico`) REFERENCES `tecnico` (`id_tecnico`);

--
-- Filtros para la tabla `historial`
--
ALTER TABLE `historial`
  ADD CONSTRAINT `historial_ibfk_1` FOREIGN KEY (`id_solicitud`) REFERENCES `solicitud` (`id_solicitud`),
  ADD CONSTRAINT `historial_ibfk_2` FOREIGN KEY (`id_tecnico`) REFERENCES `tecnico` (`id_tecnico`),
  ADD CONSTRAINT `historial_ibfk_3` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);

--
-- Filtros para la tabla `lugar_trabajo`
--
ALTER TABLE `lugar_trabajo`
  ADD CONSTRAINT `lugar_trabajo_ibfk_1` FOREIGN KEY (`id_comuna`) REFERENCES `comunas` (`id_comuna`),
  ADD CONSTRAINT `lugar_trabajo_ibfk_2` FOREIGN KEY (`id_region`) REFERENCES `regiones` (`id_region`),
  ADD CONSTRAINT `lugar_trabajo_ibfk_3` FOREIGN KEY (`id_tecnico`) REFERENCES `tecnico` (`id_tecnico`);

--
-- Filtros para la tabla `solicitud`
--
ALTER TABLE `solicitud`
  ADD CONSTRAINT `solicitud_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `solicitud_ibfk_2` FOREIGN KEY (`id_tecnico`) REFERENCES `tecnico` (`id_tecnico`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `solicitud_ibfk_3` FOREIGN KEY (`id_servicio`) REFERENCES `servicio` (`id_servicio`) ON DELETE NO ACTION ON UPDATE CASCADE,
  ADD CONSTRAINT `solicitud_ibfk_4` FOREIGN KEY (`estado_solicitud`) REFERENCES `estado_solicitud` (`id_estado_solicitud`) ON UPDATE CASCADE,
  ADD CONSTRAINT `solicitud_ibfk_6` FOREIGN KEY (`id_region`) REFERENCES `regiones` (`id_region`),
  ADD CONSTRAINT `solicitud_ibfk_7` FOREIGN KEY (`id_comuna`) REFERENCES `comunas` (`id_comuna`);

--
-- Filtros para la tabla `tecnico`
--
ALTER TABLE `tecnico`
  ADD CONSTRAINT `tecnico_ibfk_1` FOREIGN KEY (`id_servicio`) REFERENCES `servicio` (`id_servicio`),
  ADD CONSTRAINT `tecnico_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);

--
-- Filtros para la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD CONSTRAINT `usuario_ibfk_3` FOREIGN KEY (`tipo_usuario`) REFERENCES `rol` (`id_rol`) ON DELETE NO ACTION,
  ADD CONSTRAINT `usuario_ibfk_6` FOREIGN KEY (`comuna`) REFERENCES `comunas` (`id_comuna`) ON DELETE NO ACTION,
  ADD CONSTRAINT `usuario_ibfk_7` FOREIGN KEY (`region`) REFERENCES `regiones` (`id_region`) ON DELETE NO ACTION;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
