from flask import Flask, render_template, request, session, redirect, url_for
from flask_babel import Babel, gettext as _
import os

app = Flask(__name__)
app.secret_key = 'palabra_secreta_super_segura'

def get_locale():
    lang = request.args.get('lang')
    if lang in ['en', 'es', 'ca']:
        session['lang'] = lang
    return session.get('lang', 'es')

app.config['BABEL_DEFAULT_LOCALE'] = 'es'
babel = Babel(app, locale_selector=get_locale)

TARGET_WORDS = {
    'en': 'WORLD',
    'es': 'MUNDO',
    'ca': 'POBLE'  
}

@app.route('/', methods=['GET', 'POST'])
def index():
    lang = get_locale()
    target = TARGET_WORDS.get(lang, 'MUNDO')
    
    if 'guesses' not in session:
        session['guesses'] = []
    if 'game_over' not in session:
        session['game_over'] = False

    message = ""
    
    if request.method == 'POST' and not session['game_over']:
        guess = request.form.get('guess', '').upper().strip()
        
        if len(guess) != 5:
            message = _("La palabra debe tener 5 letras.")
        else:

            result = []
            for i, letter in enumerate(guess):
                status = 'absent' 
                if letter == target[i]:
                    status = 'correct' 
                elif letter in target:
                    status = 'present'
                result.append({'letter': letter, 'status': status})
            
            guesses = session['guesses']
            guesses.append(result)
            session['guesses'] = guesses
            
            if guess == target:
                message = _("¡HAS GANADO! Eres increíble.")
                session['game_over'] = True
            elif len(guesses) >= 6:
                message = _("Juego terminado. La palabra era: %(word)s", word=target)
                session['game_over'] = True

    return render_template('index.html', guesses=session['guesses'], message=message, game_over=session['game_over'])

@app.route('/reset')
def reset():
    lang = session.get('lang', 'es')
    session.clear()
    session['lang'] = lang
    return redirect(url_for('index'))

if __name__ == '__main__':
    app.run(debug=True)
