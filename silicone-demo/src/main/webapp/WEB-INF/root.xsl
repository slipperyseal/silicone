<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="html"/>
    <xsl:strip-space elements="*"/>
    <xsl:output indent="no" method="html"/>
    <xsl:template match="/Backing">
        <html>
            <head>
                <meta charset="UTF-8"/>
                <title>Silicone Airlines</title>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <link href="{pathOffset}css/bootstrap.min.css" rel="stylesheet"/>
                <link href="{pathOffset}css/purestyle.css" rel="stylesheet" media="all"/>
                <link href="{pathOffset}favicon.ico" rel="icon"/>
            </head>
            <body>
                <script src="https://code.jquery.com/jquery.js"/>
                <script src="{pathOffset}js/bootstrap.min.js"/>

                <div id="topstrip">
                    <xsl:for-each select="notice">
                        <p><xsl:value-of select="."/></p>
                    </xsl:for-each>
                </div>
                <div class="topspacer"/>
                <h2>Silicone Airlines</h2>

                <xsl:for-each select="artefacts/Logout">
                    <form method="post">
                        <input type="hidden" name="action" value="Logout"/>
                        <input name="logout" type="submit" value="Logout"/>
                    </form>
                </xsl:for-each>

                <xsl:for-each select="status">
                    <xsl:if test=". = '404'">
                        <div class="container">
                            <h1>404 Not Found. Awkward.</h1>
                        </div>
                    </xsl:if>
                </xsl:for-each>

                <div class="outer">
                    <div class="outermain">
                        <xsl:for-each select="artefacts">
                            <xsl:for-each select="Flights">
                                <div class="board">
                                    <div class="boardheader">Flight</div>
                                    <div class="boardheader">Departs</div>
                                    <div class="boardheader">Arrives</div>
                                    <div class="boardheader">Sched</div>
                                    <div class="boardheader">Gate</div>
                                    <div class="boardheader">Status</div>
                                    <xsl:for-each select="flights/*">
                                        <div class="boardcell"><xsl:value-of select="number"/></div>
                                        <div class="boardcell"><xsl:value-of select="origin"/></div>
                                        <div class="boardcell"><xsl:value-of select="destination"/></div>
                                        <div class="boardcell"><xsl:value-of select="departureTime"/></div>
                                        <div class="boardcell"><xsl:value-of select="gate"/></div>
                                        <div class="boardcell">ON TIME</div>
                                    </xsl:for-each>
                                </div>
                            </xsl:for-each>
                        </xsl:for-each>
                    </div>
                    <div class="outerlogin">
                        <xsl:for-each select="artefacts">
                            <xsl:for-each select="Login">
                                <form class="form-signin" role="form" method="post">
                                    <input type="hidden" name="action" value="Login"/>
                                    <input type="text" class="form-control" name="name" value="{name}"
                                           placeholder="Username" required="true" autofocus="true"/>
                                    <input type="password" class="form-control" name="password"
                                           placeholder="Password" required="true"/>
                                    <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
                                </form>
                            </xsl:for-each>

                            <xsl:for-each select="CreateUser">
                                <form class="form-signin" role="form" method="post">
                                    <input type="hidden" name="action" value="CreateUser"/>
                                    <input type="text" class="form-control" name="name" value="{name}" placeholder="Username" required="true"/>
                                    <input type="password" class="form-control" name="password" placeholder="Choose a password" required="true"/>
                                    <button class="btn btn-lg btn-primary btn-block" type="submit">Create your account</button>
                                </form>
                            </xsl:for-each>

                            <xsl:for-each select="BookFlight">
                                <form class="form-signin" role="form" method="post">
                                    <input type="hidden" name="action" value="BookFlight"/>
                                    <input type="hidden" name="auth" value="{/Backing/auth}"/>
                                    <input type="text" class="form-control" name="flight" value="{flight}" placeholder="flight number" required="true"/>
                                    <button class="btn btn-lg btn-primary btn-block" type="submit">Book your flight</button>
                                </form>

                                <h3>Bookings</h3>
                                <div class="bookings">
                                    <div class="boardheader">Flight</div>
                                    <div class="boardheader">Departs</div>
                                    <div class="boardheader">Arrives</div>
                                    <div class="boardheader">Sched</div>
                                    <xsl:for-each select="../Bookings/Flight">
                                        <div class="boardcell"><xsl:value-of select="number"/></div>
                                        <div class="boardcell"><xsl:value-of select="origin"/></div>
                                        <div class="boardcell"><xsl:value-of select="destination"/></div>
                                        <div class="boardcell"><xsl:value-of select="departureTime"/></div>
                                    </xsl:for-each>
                                </div>
                            </xsl:for-each>
                        </xsl:for-each>
                    </div>
                </div>
            </body>
        </html>
    </xsl:template>
</xsl:stylesheet>
