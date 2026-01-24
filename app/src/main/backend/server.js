const express = require('express');
const bodyParser = require('body-parser');
const cors = require('cors');
const sqlite3 = require('sqlite3').verbose();

const app = express();
app.use(bodyParser.json());
app.use(cors());

const db = new sqlite3.Database('./subtrack_server.db');

db.serialize(() => {

    db.run("CREATE TABLE IF NOT EXISTS utilizador (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");

    db.run("INSERT OR IGNORE INTO utilizador (id, username, password) VALUES (1, 'user', 'pass')");


    db.run("CREATE TABLE IF NOT EXISTS assinaturas (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, valor REAL, usuario_id INTEGER)");
});



app.post('/login', (req, res) => {
    const { username, password } = req.body;
    db.get("SELECT * FROM utilizador WHERE username = ? AND password = ?", [username, password], (err, row) => {
        if (row) {
            res.json({ id: row.id, username: row.username });
        } else {
            res.status(401).json({ error: "Login inválido" });
        }
    });
});


app.get('/subscriptions', (req, res) => {

    db.all("SELECT * FROM assinaturas", [], (err, rows) => {
        if (err) res.status(500).send(err);
        else res.json(rows);
    });
});

app.post('/subscriptions', (req, res) => {
    const { nome, valor, usuarioId } = req.body;
    const stmt = db.prepare("INSERT INTO assinaturas (nome, valor, usuario_id) VALUES (?, ?, ?)");
    stmt.run(nome, valor, usuarioId, function(err) {
        if (err) res.status(500).send(err);
        else res.json({ id: this.lastID, nome, valor, usuarioId });
    });
    stmt.finalize();
});

app.get('/subscriptions/:id', (req, res) => {
    db.get("SELECT * FROM assinaturas WHERE id = ?", [req.params.id], (err, row) => {
        if (row) res.json(row);
        else res.status(404).json({ error: "Não encontrado" });
    });
});

app.put('/subscriptions/:id', (req, res) => {
    const { nome, valor } = req.body;
    db.run("UPDATE assinaturas SET nome = ?, valor = ? WHERE id = ?", [nome, valor, req.params.id], function(err) {
        if (err) res.status(500).send(err);
        else res.json({ message: "Atualizado" });
    });
});

app.delete('/subscriptions/:id', (req, res) => {
    db.run("DELETE FROM assinaturas WHERE id = ?", [req.params.id], function(err) {
        if (err) res.status(500).send(err);
        else res.json({ message: "Deletado" });
    });
});

app.listen(3000, '0.0.0.0', () => {
    console.log('Servidor rodando na porta 3000 para todos os IPs');
});