package main;

import modelo.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClinicaDental {
    private static List<Paciente> pacientes = new ArrayList<>();
    private static List<Doctor> doctores = new ArrayList<>();
    private static List<Cita> citas = new ArrayList<>();
    private static List<Tratamiento> tratamientos = new ArrayList<>();
    private static List<Factura> facturas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Agregar datos hardcoded para pruebas
        agregarDatosHardcoded();

        // Menú principal
        while (true) {
            System.out.println("\n--- Sistema de Gestión de Clínica Dental ---");
            System.out.println("1. Gestión de Pacientes");
            System.out.println("2. Gestión de Doctores");
            System.out.println("3. Gestión de Citas");
            System.out.println("4. Gestión de Tratamientos");
            System.out.println("5. Gestión de Facturas");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            int opcion = 0;
            try {
                opcion = scanner.nextInt();
                scanner.nextLine(); // Consumir el salto de línea
            } catch (Exception e) {
                System.out.println("❌ Error: Ingrese un número válido.");
                scanner.nextLine(); // Limpiar el buffer del scanner
                continue;
            }

            switch (opcion) {
                case 1:
                    gestionPacientes();
                    break;
                case 2:
                    gestionDoctores();
                    break;
                case 3:
                    gestionCitas();
                    break;
                case 4:
                    gestionTratamientos();
                    break;
                case 5:
                    gestionFacturas();
                    break;
                case 6:
                    System.out.println("Saliendo del sistema...");
                    scanner.close();
                    return;
                default:
                    System.out.println("❌ Opción no válida. Intente de nuevo.");
            }
        }
    }

    // Método para agregar datos hardcoded
    private static void agregarDatosHardcoded() {
        // Crear pacientes hardcoded
        Paciente paciente1 = new Paciente("Juan", "Pérez", 30, "12345678-9", 12345678, "juan.perez@example.com");
        pacientes.add(paciente1);

        // Crear doctores hardcoded
        Doctor doctor1 = new Doctor(1000, "Carlos", "García", "Odontología", 87654321, "carlos.garcia@clinica.com");
        doctores.add(doctor1);

        // Crear citas hardcoded
        LocalDateTime horaInicio = LocalDateTime.of(2025, 10, 25, 10, 0);
        LocalDateTime horaFin = LocalDateTime.of(2025, 10, 25, 11, 0);
        Cita cita1 = new Cita("C001", paciente1, doctor1, horaInicio, horaFin, 100.0);
        citas.add(cita1);

        // Crear tratamientos hardcoded
        Tratamiento tratamiento1 = new Tratamiento("T001", paciente1, doctor1, "Limpieza dental", 150.0, LocalDateTime.now(), "Pendiente");
        tratamientos.add(tratamiento1);

        // Crear facturas hardcoded
        Factura factura1 = new Factura("F001", paciente1, List.of(cita1), List.of(tratamiento1), LocalDateTime.now(), 250.0, "Pendiente");
        facturas.add(factura1);

    }

    // Métodos para la gestión de pacientes
    private static void gestionPacientes() {
        while (true) {
            System.out.println("\n--- Gestión de Pacientes ---");
            System.out.println("1. Registrar nuevo paciente");
            System.out.println("2. Actualizar información del paciente");
            System.out.println("3. Consultar historial médico");
            System.out.println("4. Eliminar paciente");
            System.out.println("5. Mostrar todos los pacientes");
            System.out.println("6. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    registrarPaciente();
                    break;
                case 2:
                    actualizarPaciente();
                    break;
                case 3:
                    consultarHistorial();
                    break;
                case 4:
                    eliminarPaciente();
                    break;
                case 5:
                    mostrarPacientes(); // Nueva opción para mostrar pacientes
                    break;
                case 6:
                    return;
                default:
                    System.out.println("❌ Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void registrarPaciente() {
        try {
            System.out.print("Ingrese el nombre del paciente: ");
            String nombre = scanner.nextLine();
            if (nombre.isEmpty()) {
                throw new IllegalArgumentException("❌ El nombre no puede estar vacío.");
            }

            System.out.print("Ingrese el apellido del paciente: ");
            String apellido = scanner.nextLine();
            if (apellido.isEmpty()) {
                throw new IllegalArgumentException("❌ El apellido no puede estar vacío.");
            }

            System.out.print("Ingrese la edad del paciente: ");
            int edad;
            try {
                edad = Integer.parseInt(scanner.nextLine());
                if (edad <= 0) {
                    throw new IllegalArgumentException("❌ La edad debe ser un número positivo.");
                }
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("❌ La edad debe ser un número válido.");
            }

            System.out.print("Ingrese el DUI del paciente (formato: 12345678-9): ");
            String DUI = scanner.nextLine();
            if (DUI.isEmpty()) {
                throw new IllegalArgumentException("❌ El DUI no puede estar vacío.");
            }
            if (DUI.length() != 10 || !DUI.matches("\\d{8}-\\d{1}")) {
                throw new IllegalArgumentException("❌ El DUI debe tener 9 caracteres (formato: 12345678-9).");
            }

            System.out.print("Ingrese el teléfono del paciente (8 dígitos): ");
            String telefonoStr = scanner.nextLine();
            if (telefonoStr.isEmpty()) {
                throw new IllegalArgumentException("❌ El teléfono no puede estar vacío.");
            }
            if (telefonoStr.length() != 8 || !telefonoStr.matches("\\d{8}")) {
                throw new IllegalArgumentException("❌ El teléfono debe tener exactamente 8 dígitos.");
            }
            int telefono = Integer.parseInt(telefonoStr);

            System.out.print("Ingrese el correo del paciente: ");
            String correo = scanner.nextLine();
            if (correo.isEmpty()) {
                throw new IllegalArgumentException("❌ El correo no puede estar vacío.");
            }
            if (!correo.contains("@")) {
                throw new IllegalArgumentException("❌ El correo debe contener un '@'.");
            }

            // Si todas las validaciones pasan, se registra el paciente
            Paciente paciente = new Paciente(nombre, apellido, edad, DUI, telefono, correo);
            pacientes.add(paciente);
            System.out.println("✅ Paciente registrado exitosamente.");

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // Mostrar el mensaje de error
        } catch (Exception e) {
            System.out.println("❌ Ocurrió un error inesperado: " + e.getMessage());
        }
    }

    private static void actualizarPaciente() {
        System.out.print("Ingrese el DUI del paciente a actualizar: ");
        String DUI = scanner.nextLine();
        Paciente paciente = buscarPacientePorDUI(DUI);

        if (paciente == null) {
            System.out.println("❌ Paciente no encontrado.");
            return;
        }

        System.out.print("Ingrese el nuevo nombre del paciente: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese el nuevo apellido del paciente: ");
        String apellido = scanner.nextLine();
        System.out.print("Ingrese la nueva edad del paciente: ");
        int edad = scanner.nextInt();
        scanner.nextLine(); // Consumir el salto de línea
        System.out.print("Ingrese el nuevo teléfono del paciente: ");
        int telefono = Integer.parseInt(scanner.nextLine());
        System.out.print("Ingrese el nuevo correo del paciente: ");
        String correo = scanner.nextLine();

        paciente.setNombre(nombre);
        paciente.setApellido(apellido);
        paciente.setEdad(edad);
        paciente.setTelefono(telefono);
        paciente.setCorreo(correo);
        System.out.println("✅ Paciente actualizado exitosamente.");
    }

    private static void consultarHistorial() {
        System.out.print("Ingrese el DUI del paciente: ");
        String DUI = scanner.nextLine();
        Paciente paciente = buscarPacientePorDUI(DUI);

        if (paciente == null) {
            System.out.println("❌ Paciente no encontrado.");
            return;
        }

        paciente.consultarHistorial();
    }

    private static void eliminarPaciente() {
        System.out.print("Ingrese el DUI del paciente a eliminar: ");
        String DUI = scanner.nextLine();
        Paciente paciente = buscarPacientePorDUI(DUI);

        if (paciente == null) {
            System.out.println("❌ Paciente no encontrado.");
            return;
        }

        pacientes.remove(paciente);
        System.out.println("✅ Paciente eliminado exitosamente.");
    }
    private static void mostrarPacientes() {
        if (pacientes.isEmpty()) {
            System.out.println("❌ No hay pacientes registrados.");
            return;
        }

        System.out.println("\n--- Lista de Pacientes Registrados ---");
        System.out.println("+---------------------+---------------------+---------------------+---------------------+---------------------+---------------------+");
        System.out.println("| Nombre              | Apellido            | DUI                 | Edad                | Teléfono            | Correo              |");
        System.out.println("+---------------------+---------------------+---------------------+---------------------+---------------------+---------------------+");

        for (Paciente paciente : pacientes) {
            System.out.printf(
                    "| %-20s| %-20s| %-20s| %-20d| %-20d| %-20s|\n",
                    paciente.getNombre(),
                    paciente.getApellido(),
                    paciente.getDUI(),
                    paciente.getEdad(),
                    paciente.getTelefono(),
                    paciente.getCorreo()
            );
        }

        System.out.println("+---------------------+---------------------+---------------------+---------------------+---------------------+---------------------+");
    }

    private static Paciente buscarPacientePorDUI(String DUI) {
        for (Paciente paciente : pacientes) {
            if (paciente.getDUI().equals(DUI)) {
                return paciente;
            }
        }
        return null;
    }

    // Métodos para la gestión de doctores
    private static void gestionDoctores() {
        while (true) {
            System.out.println("\n--- Gestión de Doctores ---");
            System.out.println("1. Registrar nuevo doctor");
            System.out.println("2. Consultar citas de un doctor");
            System.out.println("3. Mostrar todos los doctores");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    registrarDoctor();
                    break;
                case 2:
                    consultarCitasDoctor();
                    break;
                case 3:
                    mostrarDoctores(); // Nueva opción para mostrar doctores
                    break;
                case 4:
                    return;
                default:
                    System.out.println("❌ Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void registrarDoctor() {
        try {
            System.out.print("Ingrese el nombre del doctor: ");
            String nombre = scanner.nextLine();
            if (nombre.isEmpty()) {
                throw new IllegalArgumentException("❌ El nombre no puede estar vacío.");
            }

            System.out.print("Ingrese el apellido del doctor: ");
            String apellido = scanner.nextLine();
            if (apellido.isEmpty()) {
                throw new IllegalArgumentException("❌ El apellido no puede estar vacío.");
            }

            System.out.print("Ingrese la especialidad del doctor: ");
            String especialidad = scanner.nextLine();
            if (especialidad.isEmpty()) {
                throw new IllegalArgumentException("❌ La especialidad no puede estar vacía.");
            }

            System.out.print("Ingrese el teléfono del doctor (8 dígitos): ");
            String telefonoStr = scanner.nextLine();
            if (telefonoStr.isEmpty()) {
                throw new IllegalArgumentException("❌ El teléfono no puede estar vacío.");
            }
            if (telefonoStr.length() != 8 || !telefonoStr.matches("\\d{8}")) {
                throw new IllegalArgumentException("❌ El teléfono debe tener exactamente 8 dígitos.");
            }
            int telefono = Integer.parseInt(telefonoStr);

            System.out.print("Ingrese el correo del doctor: ");
            String correo = scanner.nextLine();
            if (correo.isEmpty()) {
                throw new IllegalArgumentException("❌ El correo no puede estar vacío.");
            }
            if (!correo.contains("@")) {
                throw new IllegalArgumentException("❌ El correo debe contener un '@'.");
            }

            // Generar un ID único de 4 dígitos
            int idDoctor = generarIdUnico();

            // Crear el doctor con el ID generado
            Doctor doctor = new Doctor(idDoctor, nombre, apellido, especialidad, telefono, correo);
            doctores.add(doctor);
            System.out.println("✅ Doctor registrado exitosamente con ID: " + idDoctor);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage()); // Mostrar el mensaje de error
        } catch (Exception e) {
            System.out.println("❌ Ocurrió un error inesperado: " + e.getMessage());
        }
    }

    private static void consultarCitasDoctor() {
        System.out.print("Ingrese el ID del doctor: ");
        int idDoctor = Integer.parseInt(scanner.nextLine());
        Doctor doctor = buscarDoctorPorId(idDoctor);

        if (doctor == null) {
            System.out.println("❌ Doctor no encontrado.");
            return;
        }

        doctor.consultarCitas();
    }
    private static void mostrarDoctores() {
        if (doctores.isEmpty()) {
            System.out.println("❌ No hay doctores registrados.");
            return;
        }

        // Ordenar la lista de doctores por ID
        doctores.sort((d1, d2) -> Integer.compare(d1.getId(), d2.getId()));

        System.out.println("\n--- Lista de Doctores Registrados ---");
        System.out.println("+---------------------+---------------------+---------------------+---------------------+---------------------+---------------------+");
        System.out.println("| ID                  | Nombre              | Apellido            | Especialidad        | Teléfono            | Correo              |");
        System.out.println("+---------------------+---------------------+---------------------+---------------------+---------------------+---------------------+");

        for (Doctor doctor : doctores) {
            System.out.printf(
                    "| %-20d| %-20s| %-20s| %-20s| %-20d| %-20s|\n",
                    doctor.getId(),
                    doctor.getNombre(),
                    doctor.getApellido(),
                    doctor.getEspecialidad(),
                    doctor.getTelefono(),
                    doctor.getCorreo()
            );
        }

        System.out.println("+---------------------+---------------------+---------------------+---------------------+---------------------+---------------------+");
    }
    private static Doctor buscarDoctorPorId(int id) {
        for (Doctor doctor : doctores) {
            if (doctor.getId() == id) {
                return doctor;
            }
        }
        return null;
    }

    // Método para generar un ID único de 4 dígitos
    private static int generarIdUnico() {
        int id = 1000; // Comenzamos desde 1000 para asegurar 4 dígitos

        // Buscar el siguiente ID disponible
        while (true) {
            boolean idEnUso = false;

            // Verificar si el ID ya está en uso
            for (Doctor doctor : doctores) {
                if (doctor.getId() == id) {
                    idEnUso = true;
                    break;
                }
            }

            // Si el ID no está en uso, lo retornamos
            if (!idEnUso) {
                return id;
            }

            // Incrementar el ID para probar el siguiente
            id++;

            // Si llegamos a un ID muy grande (por ejemplo, 9999), reiniciamos
            if (id > 9999) {
                id = 1000; // Reiniciamos a 1000
            }
        }
    }


    // Métodos para la gestión de citas
    private static void gestionCitas() {
        while (true) {
            System.out.println("\n--- Gestión de Citas ---");
            System.out.println("1. Agendar cita");
            System.out.println("2. Cancelar cita");
            System.out.println("3. Modificar cita");
            System.out.println("4. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    agendarCita();
                    break;
                case 2:
                    cancelarCita();
                    break;
                case 3:
                    modificarCita();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("❌ Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void agendarCita() {
        System.out.print("Ingrese el DUI del paciente: ");
        String DUI = scanner.nextLine();
        Paciente paciente = buscarPacientePorDUI(DUI);

        if (paciente == null) {
            System.out.println("❌ Paciente no encontrado.");
            return;
        }

        System.out.print("Ingrese el ID del doctor: ");
        int idDoctor = Integer.parseInt(scanner.nextLine());
        Doctor doctor = buscarDoctorPorId(idDoctor);

        if (doctor == null) {
            System.out.println("❌ Doctor no encontrado.");
            return;
        }

        System.out.print("Ingrese la fecha y hora de inicio de la cita (yyyy-MM-dd HH:mm): ");
        String fechaInicioStr = scanner.nextLine();
        LocalDateTime horaInicio;

        try {
            horaInicio = LocalDateTime.parse(fechaInicioStr.replace(" ", "T"));
        } catch (Exception e) {
            System.out.println("❌ Error: Formato de fecha inválido. Use el formato yyyy-MM-dd HH:mm.");
            return;
        }

        System.out.print("Ingrese la fecha y hora de fin de la cita (yyyy-MM-dd HH:mm): ");
        String fechaFinStr = scanner.nextLine();
        LocalDateTime horaFin;

        try {
            horaFin = LocalDateTime.parse(fechaFinStr.replace(" ", "T"));
        } catch (Exception e) {
            System.out.println("❌ Error: Formato de fecha inválido. Use el formato yyyy-MM-dd HH:mm.");
            return;
        }

        // Verificar disponibilidad del doctor
        if (!doctor.estaDisponible(horaInicio, horaFin)) {
            System.out.println("❌ El doctor no está disponible en ese horario.");
            return;
        }

        System.out.print("Ingrese el costo de la cita: ");
        double costo;

        try {
            costo = scanner.nextDouble();
            scanner.nextLine(); // Consumir el salto de línea
        } catch (Exception e) {
            System.out.println("❌ Error: Ingrese un número válido para el costo.");
            scanner.nextLine(); // Limpiar el buffer del scanner
            return;
        }

        // Generar un ID secuencial para la cita
        String idCita = "C" + (citas.size() + 1);

        // Crear la cita con el ID generado
        Cita cita = new Cita(idCita, paciente, doctor, horaInicio, horaFin, costo);
        citas.add(cita);

        // Agregar la cita al historial del paciente
        paciente.agregarCita(cita);

        // Agregar la cita al doctor
        doctor.agregarCita(cita);

        System.out.println("✅ Cita agendada exitosamente con ID: " + idCita);
    }

    private static void cancelarCita() {
        System.out.print("Ingrese el ID de la cita a cancelar: ");
        String idCita = scanner.nextLine();
        Cita cita = buscarCitaPorId(idCita);

        if (cita == null) {
            System.out.println("❌ Cita no encontrada.");
            return;
        }

        // Actualizar el estado de la cita a "Cancelada"
        cita.setEstado("Cancelada");

        // Actualizar la cita en la lista de citas del paciente
        Paciente paciente = cita.getPaciente();
        paciente.getCitas().removeIf(c -> c.getIdCita().equals(idCita)); // Eliminar la cita anterior
        paciente.agregarCita(cita); // Agregar la cita actualizada

        // Actualizar la cita en la lista de citas del doctor
        Doctor doctor = cita.getDoctor();
        doctor.getCitas().removeIf(c -> c.getIdCita().equals(idCita)); // Eliminar la cita anterior
        doctor.agregarCita(cita); // Agregar la cita actualizada

          System.out.println("✅ Cita cancelada exitosamente.");
    }

    private static void modificarCita() {
        System.out.print("Ingrese el ID de la cita a modificar: ");
        String idCita = scanner.nextLine();
        Cita cita = buscarCitaPorId(idCita);

        if (cita == null) {
            System.out.println("❌ Cita no encontrada.");
            return;
        }

        System.out.print("Ingrese la nueva fecha y hora de inicio de la cita (yyyy-MM-dd HH:mm): ");
        String fechaInicioStr = scanner.nextLine();
        LocalDateTime nuevaHoraInicio;

        try {
            nuevaHoraInicio = LocalDateTime.parse(fechaInicioStr.replace(" ", "T"));
        } catch (Exception e) {
            System.out.println("❌ Error: Formato de fecha inválido. Use el formato yyyy-MM-dd HH:mm.");
            return;
        }

        System.out.print("Ingrese la nueva fecha y hora de fin de la cita (yyyy-MM-dd HH:mm): ");
        String fechaFinStr = scanner.nextLine();
        LocalDateTime nuevaHoraFin;

        try {
            nuevaHoraFin = LocalDateTime.parse(fechaFinStr.replace(" ", "T"));
        } catch (Exception e) {
            System.out.println("❌ Error: Formato de fecha inválido. Use el formato yyyy-MM-dd HH:mm.");
            return;
        }

        // Verificar disponibilidad del doctor
        if (!cita.getDoctor().estaDisponible(nuevaHoraInicio, nuevaHoraFin)) {
            System.out.println("❌ El doctor no está disponible en ese horario.");
            return;
        }

        cita.setHoraInicio(nuevaHoraInicio);
        cita.setHoraFin(nuevaHoraFin);
        System.out.println("✅ Cita modificada exitosamente.");
    }

    private static Cita buscarCitaPorId(String idCita) {
        for (Cita cita : citas) {
            if (cita.getIdCita().equals(idCita)) {
                return cita;
            }
        }
        return null;
    }

    // Métodos para la gestión de tratamientos
    private static void gestionTratamientos() {
        while (true) {
            System.out.println("\n--- Gestión de Tratamientos ---");
            System.out.println("1. Registrar tratamiento");
            System.out.println("2. Consultar tratamientos de un paciente");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    registrarTratamiento();
                    break;
                case 2:
                    consultarTratamientosPaciente();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("❌ Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void registrarTratamiento() {
        System.out.print("Ingrese el DUI del paciente: ");
        String DUI = scanner.nextLine();
        Paciente paciente = buscarPacientePorDUI(DUI);

        if (paciente == null) {
            System.out.println("❌ Paciente no encontrado.");
            return;
        }

        System.out.print("Ingrese el ID del doctor: ");
        int idDoctor = Integer.parseInt(scanner.nextLine());
        Doctor doctor = buscarDoctorPorId(idDoctor);

        if (doctor == null) {
            System.out.println("❌ Doctor no encontrado.");
            return;
        }

        System.out.print("Ingrese la descripción del tratamiento: ");
        String descripcion = scanner.nextLine();

        System.out.print("Ingrese el costo del tratamiento: ");
        double costo;

        try {
            costo = scanner.nextDouble();
            scanner.nextLine(); // Consumir el salto de línea
        } catch (Exception e) {
            System.out.println("❌ Error: Ingrese un número válido para el costo.");
            scanner.nextLine(); // Limpiar el buffer del scanner
            return;
        }

        // Generar un ID secuencial para el tratamiento
        String idTratamiento = "T" + (tratamientos.size() + 1);

        // Crear el tratamiento con el ID generado
        Tratamiento tratamiento = new Tratamiento(idTratamiento, paciente, doctor, descripcion, costo, LocalDateTime.now(), "Pendiente");
        tratamientos.add(tratamiento);

        // Agregar el tratamiento al historial del paciente
        paciente.agregarTratamiento(tratamiento);

        System.out.println("✅ Tratamiento registrado exitosamente con ID: " + idTratamiento);
    }

    private static void consultarTratamientosPaciente() {
        System.out.print("Ingrese el DUI del paciente: ");
        String DUI = scanner.nextLine();
        Paciente paciente = buscarPacientePorDUI(DUI);

        if (paciente == null) {
            System.out.println("❌ Paciente no encontrado.");
            return;
        }

        System.out.println("\n--- Tratamientos del Paciente " + paciente.getNombre() + " " + paciente.getApellido() + " ---");
        boolean tieneTratamientos = false;

        for (Tratamiento tratamiento : tratamientos) {
            if (tratamiento.getPaciente().equals(paciente)) {
                System.out.println("\nID del tratamiento: " + tratamiento.getIdTratamiento());
                System.out.println("Descripción: " + tratamiento.getDescripcion());
                System.out.println("Costo: $" + tratamiento.getCosto());
                System.out.println("Fecha de realización: " + tratamiento.getFechaRealizacion());
                System.out.println("Estado: " + tratamiento.getEstado());
                tieneTratamientos = true;
            }
        }

        if (!tieneTratamientos) {
            System.out.println("El paciente no tiene tratamientos registrados.");
        }
    }

    // Métodos para la gestión de facturas
    private static void gestionFacturas() {
        while (true) {
            System.out.println("\n--- Gestión de Facturas ---");
            System.out.println("1. Generar factura");
            System.out.println("2. Registrar pago de factura");
            System.out.println("3. Volver al menú principal");
            System.out.print("Seleccione una opción: ");

            int opcion = scanner.nextInt();
            scanner.nextLine(); // Consumir el salto de línea

            switch (opcion) {
                case 1:
                    generarFactura();
                    break;
                case 2:
                    registrarPagoFactura();
                    break;
                case 3:
                    return;
                default:
                    System.out.println("❌ Opción no válida. Intente de nuevo.");
            }
        }
    }

    private static void generarFactura() {
        System.out.print("Ingrese el DUI del paciente: ");
        String DUI = scanner.nextLine();
        Paciente paciente = buscarPacientePorDUI(DUI);

        if (paciente == null) {
            System.out.println("❌ Paciente no encontrado.");
            return;
        }

        System.out.print("Ingrese el ID del doctor: ");
        int idDoctor = Integer.parseInt(scanner.nextLine());
        Doctor doctor = buscarDoctorPorId(idDoctor);

        if (doctor == null) {
            System.out.println("❌ Doctor no encontrado.");
            return;
        }

        System.out.print("Ingrese la fecha y hora de inicio de la cita (yyyy-MM-dd HH:mm): ");
        String fechaInicioStr = scanner.nextLine();
        LocalDateTime horaInicio;

        try {
            horaInicio = LocalDateTime.parse(fechaInicioStr.replace(" ", "T"));
        } catch (Exception e) {
            System.out.println("❌ Error: Formato de fecha inválido. Use el formato yyyy-MM-dd HH:mm.");
            return;
        }

        System.out.print("Ingrese la fecha y hora de fin de la cita (yyyy-MM-dd HH:mm): ");
        String fechaFinStr = scanner.nextLine();
        LocalDateTime horaFin;

        try {
            horaFin = LocalDateTime.parse(fechaFinStr.replace(" ", "T"));
        } catch (Exception e) {
            System.out.println("❌ Error: Formato de fecha inválido. Use el formato yyyy-MM-dd HH:mm.");
            return;
        }

        System.out.print("Ingrese el costo de la cita: ");
        double costoCita;

        try {
            costoCita = scanner.nextDouble();
            scanner.nextLine(); // Consumir el salto de línea
        } catch (Exception e) {
            System.out.println("❌ Error: Ingrese un número válido para el costo.");
            scanner.nextLine(); // Limpiar el buffer del scanner
            return;
        }

        // Generar un ID secuencial para la cita
        String idCita = "C" + (citas.size() + 1);

        // Crear la cita con el ID generado
        Cita cita = new Cita(idCita, paciente, doctor, horaInicio, horaFin, costoCita);
        citas.add(cita);

        System.out.print("Ingrese el ID del tratamiento: ");
        String idTratamiento = scanner.nextLine();
        Tratamiento tratamiento = buscarTratamientoPorId(idTratamiento);

        if (tratamiento == null) {
            System.out.println("❌ Tratamiento no encontrado.");
            return;
        }

        // Generar un ID secuencial para la factura
        String idFactura = "F" + (facturas.size() + 1);

        // Crear la factura con el ID generado
        Factura factura = new Factura(idFactura, paciente, List.of(cita), List.of(tratamiento), LocalDateTime.now(), 0.0, "Pendiente");
        factura.calcularMontoTotal(cita, tratamiento);
        facturas.add(factura);
        factura.generarFactura();
    }

    private static void registrarPagoFactura() {
        System.out.print("Ingrese el ID de la factura: ");
        String idFactura = scanner.nextLine();
        Factura factura = buscarFacturaPorId(idFactura);

        if (factura == null) {
            System.out.println("❌ Factura no encontrada.");
            return;
        }

        factura.setEstadoPago("Pagada");
        System.out.println("✅ Pago registrado exitosamente para la factura " + idFactura);
    }

    private static Factura buscarFacturaPorId(String idFactura) {
        for (Factura factura : facturas) {
            if (factura.getIdFactura().equals(idFactura)) {
                return factura;
            }
        }
        return null;
    }

    private static Tratamiento buscarTratamientoPorId(String idTratamiento) {
        for (Tratamiento tratamiento : tratamientos) {
            if (tratamiento.getIdTratamiento().equals(idTratamiento)) {
                return tratamiento;
            }
        }
        return null;
    }
}
