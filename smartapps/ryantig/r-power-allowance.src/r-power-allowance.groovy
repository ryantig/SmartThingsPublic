/**
 *  R Power Allowance
 *
 *  Copyright 2015 SmartThings
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
definition(
    name: "R Power Allowance",
    namespace: "ryantig",
    author: "SmartThings",
    description: "Power Allowance to turn lights off after x minutes from Ben Edwards post https://community.smartthings.com/t/app-that-will-turn-off-switch-after-a-short-period/2319/2",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")


/**
 *  Power Allowance
 *
 *  Author: SmartThings
 */
preferences {
	section("When a switch turns on...") {
		input "theSwitch", "capability.switch"
	}
	section("Turn it off how many minutes later?") {
		input "minutesLater", "number", title: "When?"
	}
}

def installed() {
	log.debug "Installed with settings: ${settings}"
	subscribe(theSwitch, "switch.on", switchOnHandler, [filterEvents: false])
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	subscribe(theSwitch, "switch.on", switchOnHandler, [filterEvents: false])
}

def switchOnHandler(evt) {
	log.debug "Switch ${theSwitch} turned: ${evt.value}"
	def delay = minutesLater * 60
	log.debug "Turning off in ${minutesLater} minutes (${delay}seconds)"
	runIn(delay, turnOffSwitch)
}

def turnOffSwitch() {
	theSwitch.off()
}