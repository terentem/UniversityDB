package org.example.utilits;

import org.example.domain.dto.ProfessorDto;
import org.example.sql.SqlScript;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ResultCreator {

    public List<ProfessorDto> getProfessorsList(Connection conn, String parameter) throws SQLException {
        //Шукаємо необхідний скрипт, в залежності від вхідних параметрів для /get
        String sql = "";
        SqlScript script = new SqlScript();
        sql = script.getSql(parameter);
        System.out.println("script=" + sql);

        List<ProfessorDto> professors = new ArrayList<>();

        // Правильный синтаксис try-with-resources для автоматического закрытия stmt и rs
        try (PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ProfessorDto p = new ProfessorDto(
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getLong("department_id")
                );
                professors.add(p);
            }
        }
        return professors;
    }
}