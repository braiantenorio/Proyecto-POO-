package servicio;

import java.util.List;

import conexion.Factory;
import dao.RelacionDAO;
import modelo.Relacion;

public class RelacionServiceImp implements RelacionService {

	private RelacionDAO relacionDAO;

	public RelacionServiceImp() {
		relacionDAO = (RelacionDAO) Factory.getInstancia("RELACION");
	}

	@Override
	public void insertar(Relacion relacion) {
		relacionDAO.insertar(relacion);
	}

	@Override
	public void actualizar(Relacion relacion) {
		relacionDAO.actualizar(relacion);
	}

	@Override
	public void borrar(Relacion relacion) {
		relacionDAO.borrar(relacion);
	}

	@Override
	public List<Relacion> buscarTodos() {
		return relacionDAO.buscarTodos();
	}

}
