package com.beardream.file_system.inter;

import com.beardream.file_system.model.Token;

public interface TokenManager {

    public Token createToken(int userId);

    public boolean checkToken(Token token);

    public Token getToken(String authenrization);

    public void deleteToken(int userId);
}
