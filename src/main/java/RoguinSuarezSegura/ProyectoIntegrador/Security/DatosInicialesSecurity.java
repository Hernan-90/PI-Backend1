package RoguinSuarezSegura.ProyectoIntegrador.Security;

import RoguinSuarezSegura.ProyectoIntegrador.Model.Usuario;
import RoguinSuarezSegura.ProyectoIntegrador.Model.UsuarioRole;
import RoguinSuarezSegura.ProyectoIntegrador.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DatosInicialesSecurity implements ApplicationRunner { //Esta clase me permite crear unos usuarios user y admin desde el comienzo,
    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        BCryptPasswordEncoder cifrador = new BCryptPasswordEncoder(); //Instancio un encriptdor
        String pass = "123456";
        String passHashed = cifrador.encode(pass);

        System.out.println("La password cifrada es:" + passHashed);

        Usuario usuarioAdmin = new Usuario("admin", "admin", "admin@gmail.com", cifrador.encode("admin"), UsuarioRole.ROLE_ADMIN);
        Usuario usuarioUser = new Usuario("user","user","user@gmail.com", cifrador.encode("user"),UsuarioRole.ROLE_USER);

        usuarioRepository.save(usuarioAdmin);
        usuarioRepository.save(usuarioUser);
    }
}
