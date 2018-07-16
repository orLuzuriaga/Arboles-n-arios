# Árboles genéricos
> 1. árbol de tipo Left-Child Right-Sibling (LCRS)

> 2. programa en Java que permita gestionar los árboles genealógicos de las familias que aparecen en la serie Juego de Tronos.
>Contiene la siguiente funcionalidad.
>
>>1.Cargar de un fichero de texto los árboles genealógicos de la familia siguiendo formato del fichero del archivo adjunto.
>>   ###### public void loadFile(String pathToFile)
>>
>> 2.Consultar los integrantes de una familia. Recibe un apellido de la familia a
>> visualizar y devuelve el árbol genealógico correspondiente.
>>    ###### public LinkedTree<FamilyMember> getFamily(String surname)
>> 3.Consultar el heredero de una famila. Para ello, se proporciona un apellido y
devuelve el primer hijo varón. En caso de que no exista, se devolvería la mayor de
las hijas o el primer nieto barón (y así sucesivamente)
>> ###### public String findHeir(String surname)
>>
>>4.Puesto que se puede descubrir que una persona tiene el apellido que no le
corresponde (por ser un descendiente ilegítimo), se debe poder cambiar de
familia (por simplicidad, se mantiene el apellido original). Para ello, se debe solicitar al usuario el nombre del personaje correspondiente y a qué familia cambia, indicando el nuevo ascendiente. El cambio afectará a todos sus descendientes.
>> ###### public void changeFamily(String memberName, String newParent)
>>
>>5.Consultar parentesco. Determina si dos personajes pertencen a la misma famila.Para ello se solicita al usuario los nombres de dos personajes y comprueba si ambos son parientes o no.
>> ###### public boolean areFamily(String name1, String name2)
>>
