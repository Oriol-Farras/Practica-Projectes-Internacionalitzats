import sys
from babel.messages import frontend

if __name__ == "__main__":

    sys.argv[0] = "pybabel"
    frontend.main()