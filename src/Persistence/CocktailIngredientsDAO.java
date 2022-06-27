/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Persistence;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author cardi
 */
public class CocktailIngredientsDAO extends Data.CocktailIngredientsDO {

    private static DBConnector dbc = new DBConnector();

    public CocktailIngredientsDAO(int fk_pk_cocktail_ID, int fk_pk_ingredient_ID, int amount, String unit) {
        super(fk_pk_cocktail_ID, fk_pk_ingredient_ID, amount, unit);

    }

    public static CocktailIngredientsDAO create(int fk_pk_cocktail_ID, int fk_pk_ingredient_ID, int amount, String unit) throws Exception {
        CocktailIngredientsDAO cidao = null;

        PreparedStatement ps = dbc.getPreparedStatement("INSERT INTO cocktails_ingredients (fk_pk_cocktail_ID, fk_pk_ingredient_ID, amount, unit) "
                + "VALUES (?, ?, ?, ?)");

        ps.setInt(1, fk_pk_cocktail_ID);
        ps.setInt(2, fk_pk_ingredient_ID);
        ps.setInt(3, amount);
        ps.setString(4, unit);
        dbc.write(ps);

        return CocktailIngredientsDAO.read(fk_pk_cocktail_ID, fk_pk_ingredient_ID);
    }

    public static CocktailIngredientsDAO read(int fk_pk_cocktail_ID, int fk_pk_ingredient_ID) throws Exception {
        CocktailIngredientsDAO cidao = null;

        ResultSet rs = dbc.read("SELECT * FROM cocktails_ingredients "
                + "WHERE `fk_pk_cocktail_ID` = " + fk_pk_cocktail_ID + " AND "
                + "`fk_pk_ingredient_ID` = " + fk_pk_ingredient_ID + " ;");
        while (rs.next()) {

            cidao = new CocktailIngredientsDAO(
                    rs.getInt("fk_pk_cocktail_ID"),
                    rs.getInt("fk_pk_ingredient_ID"),
                    rs.getInt("amount"),
                    rs.getString("unit"));

            return cidao;
        }
        return null;
    }

    public CocktailIngredientsDAO update() throws Exception {
        CocktailIngredientsDAO updatedCidao = null;

        PreparedStatement ps = dbc.getPreparedStatement(
                "UPDATE USER SET"
                + "`fk_pk_cocktail_ID` = ?, "
                + "`fk_pk_ingredient_ID` = ?, "
                + "`amount` = ?, "
                + "`unit` = ? "
                + "WHERE `fk_pk_cocktail_ID` = ? ;");
        ps.setInt(1, this.getFk_pk_cocktail_ID());
        ps.setInt(2, this.getFk_pk_ingredient_ID());
        ps.setInt(3, this.getAmount());
        ps.setString(4, this.getUnit());

        dbc.write(ps);

        updatedCidao = CocktailIngredientsDAO.read(this.getFk_pk_cocktail_ID(), this.getFk_pk_ingredient_ID());

        return updatedCidao;

    }

    public static void delete(int fk_pk_cocktail_ID, int fk_pk_ingredient_ID) throws Exception {
        PreparedStatement ps = dbc.getPreparedStatement(
                "DELETE FROM USERS WHERE fk_pk_cocktail_ID = " + fk_pk_cocktail_ID
                + "AND fk_pk_ingredient_ID = " + fk_pk_ingredient_ID);
        dbc.write(ps);

    }

    public void delete() throws Exception {
        CocktailIngredientsDAO.delete(this.getFk_pk_cocktail_ID(), this.getFk_pk_ingredient_ID());
        
    }

}
