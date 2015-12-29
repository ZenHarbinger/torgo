#!/usr/bin/env python
#
# Copyright (C) 2011 Ricardo Salveti de Araujo <rsalveti@rsalveti.net>
#
# This program is free software: you can redistribute it and/or modify
# it under the terms of the GNU General Public License as published by
# the Free Software Foundation, either version 3 of the License, or
# (at your option) any later version.

"""Changelog generator for a given PPA."""

import argparse
from launchpadlib.launchpad import Launchpad
from datetime import datetime
from urllib import urlopen
from debian import deb822

VERSION = "0.1"
cachedir = "~/.launchpadlib/cache"

lp = Launchpad.login_anonymously('changelog prober', 'production', cachedir)

def print_changelog(changes):
    for change_url in changes:
        change = deb822.Changes(urlopen(change_url))
        try:
            print change.get_as_string('Changes').replace(" .\n", " \n")
            print "\n  -- %s  %s" % (change.get_as_string('Changed-By'),
                                change.get_as_string('Date'))
        except:
            pass

def args_type_date(string):
    try:
        return datetime.strptime(string, "%Y%m%d")
    except:
        raise argparse.ArgumentTypeError("invalid date format")

def args_type_lp_team(team):
    try:
        return lp.people[team]
    except:
        raise argparse.ArgumentTypeError("invalid launchpad people/team")

# Add the arguments
def create_parser():
    parser = argparse.ArgumentParser(
            description='Print Changelog for a PPA',
            epilog='If no argument is given, it will probe all the changes'
                   ' for the default series.')
    parser.add_argument('-d', '--date', help='start date to probe for changes',
            type=args_type_date, metavar="YYYYMMDD", default='19900101')
    parser.add_argument('-s', '--series', default='natty',
            help='ubuntu series to look for changes (default: natty)')
    parser.add_argument('-t', '--team', help='launchpad team that owns the PPA',
            type=args_type_lp_team, required=True)
    parser.add_argument('-p', '--ppa',
            help='ppa name to probe the changelog (default: first PPA)')
    parser.add_argument('--version', action='version',
            version='%(prog)s ' + VERSION)
    return parser

if __name__ == '__main__':
    args = create_parser().parse_args()
    # Get the desired PPA
    if args.ppa:
        ppa = args.team.getPPAByName(name=args.ppa)
    else:
        ppa = args.team.ppas[0]
    distribution = ppa.distribution.getSeries(name_or_version=args.series)

    # Generate the Changelog
    print "Changelog for %s's %s PPA (series %s) since %s" % (
            args.team.name, ppa.name, args.series, args.date)
    pub_history = ppa.getPublishedSources(distro_series=distribution,
                    created_since_date=args.date)
    pkgs_diff = [pkg.changesFileUrl() for pkg in pub_history
                                if pkg.status in ["Published", "Superseded"]]
    print_changelog(pkgs_diff)
