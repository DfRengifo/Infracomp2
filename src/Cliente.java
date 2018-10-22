
public class Cliente 
{
    private String mensaje;
    private static Servidor servidor;


    public Cliente( Servidor pServidor, String pMensaje) {

        this.mensaje = pMensaje;
        this.servidor = pServidor;
    }

    @Override
    public void run()
    {

            Mensaje m = mensajes.get(i);
       almacenar(m);
            System.out.println("El cliente almaceno el mensaje " + i);

        buff.numClientes= buff.numClientes -1;
        System.out.println("el numero de clientes es " +buff.numClientes);
    }

    public synchronized void  almacenar(Mensaje mensaje)
    {
        while (buff.tamano <= 0)
        {
            yield();
        }
        buff.almacenar(mensaje);
        synchronized(mensaje)
        {
            try {

                mensaje.wait();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
