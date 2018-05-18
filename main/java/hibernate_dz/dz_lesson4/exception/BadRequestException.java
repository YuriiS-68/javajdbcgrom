package hibernate_dz.dz_lesson4.exception;

public class BadRequestException extends Exception{
    public BadRequestException(String message){
        super(message);
    }
}
