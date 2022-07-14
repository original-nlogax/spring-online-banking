##Java Spring online banking
API:
    EVERY /instance :id
        return
            401 // user unauthorized
            403 // access to instance forbidden
            404 // instance not found

    POST /instance
        spawn calculations instance