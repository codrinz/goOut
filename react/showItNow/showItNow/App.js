import React from 'react';
import {StyleSheet, View, Text, Button, TextInput } from 'react-native';
import { StackNavigator } from 'react-navigation';
import Communications from 'react-native-communications';


const HomeScreen = ({ navigation }) => (
  <View style={styles.container}>
    <Button
    onPress={() => navigation.navigate('Contact')}
    title="Shows this week"
    style={styles.button}
     />
    <Button
      onPress={() => navigation.navigate('Contact')}
      title="Contact"
      style={styles.contactButton}
    />
  </View>
);
class ShowsScreen extends React.Component {
  static navigationOptions = {
    tabBarLabel: 'Shows this week',
  };
}
class ContactScreen extends React.Component {
	static navigationOptions = {
    tabBarLabel: 'Contact',
  };


  constructor(props) {
    super(props);
	this.state = {subject:"", body:""}
  }
  
  send_mail() {
	  Communications.email(["codrin.st26@gmail.com"],null, null, this.state.subject, this.state.body);     
  }
	
  render() {
    return (
      <View style={styles.container}> 
		<Text>
		Subject
		</Text>
		<TextInput style={styles.defaultTextInput} value={this.state.subject} onChangeText={(subject)=>this.setState({subject:subject, body:this.state.body})}>
		</TextInput>
		<Text>
			Message
		</Text>
		<TextInput style={styles.defaultMultiLine} multiline={true} value={this.state.body} onChangeText={(body)=>this.setState({subject:this.state.subject, body:body})}>
		</TextInput>
		<Button style={styles.button} title="Send" onPress={this.send_mail.bind(this)}>
		</Button>
	  </View>
    );
  }
}

const RootNavigator = StackNavigator({
  Home: {
    screen: HomeScreen,
    navigationOptions: {
      headerTitle: 'Home',
    },
  },
  Contact: {
    screen: ContactScreen,
    navigationOptions: {
      headerTitle: 'Contact',
    },
  },
});


const styles = StyleSheet.create({
  container: {
    paddingBottom: 10,
    flex: 2,
    flexDirection: 'column',
    justifyContent: 'space-between',

  },
  button: {
    flex: 1,
    color: '#000000',
    marginBottom: 50,
  },

  contactButton: {
    flex: 1,
    color: '#000000',
    justifyContent: 'flex-end',
    
  },

  defaultTextInput: {
	  height:40
  },
  defaultMultiLine: {
	  height: 250,
	  textAlignVertical: 'top'
  },
});
export default RootNavigator;
