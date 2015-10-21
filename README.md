# CustomerDetails_Java_DB
simple  java project for 
1. adding customer details
2. adding customer Usage details
3.Display usage details by customers

Databse connection with mysql database is done through JDBC api.
Project contains user id validations, date validations. etc. 

Original requirement of this project is given below.


TASK:
We
want
a
system
to
collect
users
information
and
allow
us
to
pull
up
&
enter
information
about
them.
The
information
should
be
inputted
via
command
line
prompts.
COMMAND
1:
USER
DATA
ENTRY
This
command
will
allow
us
to
collect
some
basic
information
about
the
user.
All
data
must
be
stored
inside
a
SQL
database.
• Parameters
required:
Name,
email,
country,
phone
number.
o Some
basic
validation
should
be
done
to
ensure
that:
§ Name
is
only
characters;
§ Email
has
an
@
and
a
.
in
the
correct
order.;
§ Phone
number
only
has
numbers
and
dashes.
o Duplicate
emails
should
throw
an
error
to
the
terminal
• Upon
submitting,
a
user
id
should
be
returned
back
to
the
terminal.
A
user
ID
should
be
10
characters/numbers.
COMMAND
2:
USAGE
INFORMATION
This
command
will
store
information
about
what
service
a
user
used
at
a
particular
time.
Parameters:
• User
Id
o If
the
ID
does
not
exist,
an
error
should
be
thrown
• Type
of
usage:
DATA,
VOICE
SMS
o If
a
non-­‐existant
paramater
is
entered,
an
error
should
be
thrown
• Time
stamp
o Enter
in
the
date,
month,
year
of
usage
COMMAND
3:
FETCHING
INFORMATION
This
command
will
retrieve
ALL
information
about
the
user’s
usage
history
based
on
a
time
range.
Parameters:
• User
Id
o If
the
ID
does
not
exist,
an
error
should
be
thrown.
• Start
Date
o If
start
date
>
end
date
an
error
should
be
thrown
• End
Date
o If
end
date
<
start
date
an
error
should
be
thrown
• Type
of
Data:
o DATA,
VOICE,
SMS,
ALL

