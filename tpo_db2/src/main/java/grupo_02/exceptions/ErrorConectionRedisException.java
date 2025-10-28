package grupo_02.exceptions;

public class ErrorConectionRedisException extends Exception{
    private static final long serialVersionUID = 601128023082493198L;

    public ErrorConectionRedisException(String message){
        super(message);
    }
}
